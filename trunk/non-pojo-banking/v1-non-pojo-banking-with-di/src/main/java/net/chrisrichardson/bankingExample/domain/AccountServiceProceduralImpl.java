/*
 * Copyright (c) 2005 Chris Richardson
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.chrisrichardson.bankingExample.domain;

import java.util.Calendar;
import java.util.Date;

import net.chrisrichardson.bankingExample.infrastructure.AuditingManager;
import net.chrisrichardson.bankingExample.infrastructure.BankingSecurityManager;
import net.chrisrichardson.bankingExample.infrastructure.BankingSecurityManagerWrapper;
import net.chrisrichardson.bankingExample.infrastructure.TransactionManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AccountServiceProceduralImpl implements AccountService {

  private Log logger = LogFactory.getLog(getClass());

  private final AccountDao accountDao;

  private final BankingTransactionDao bankingTransactionDao;

  private TransactionManager transactionManager;

  private AuditingManager auditingManager;

  private BankingSecurityManagerWrapper bankingSecurityWrapper;

  public AccountServiceProceduralImpl(AccountDao accountDao,
      BankingTransactionDao bankingTransactionDao,
      TransactionManager transactionManager, AuditingManager auditingManager,
      BankingSecurityManagerWrapper bankingSecurityWrapper) {
    this.accountDao = accountDao;
    this.bankingTransactionDao = bankingTransactionDao;
    this.transactionManager = transactionManager;
    this.auditingManager = auditingManager;
    this.bankingSecurityWrapper = bankingSecurityWrapper;
  }

  public BankingTransaction transfer(String fromAccountId, String toAccountId,
      double amount) {

    bankingSecurityWrapper.verifyCallerAuthorized(AccountService.class,
        "transfer");

    logger.debug("Entering AccountServiceProceduralImpl.transfer()");

    transactionManager.begin();

    auditingManager.audit(AccountService.class, "transfer", new Object[] {
        fromAccountId, toAccountId, amount });

    try {
      Account fromAccount = accountDao.findAccount(fromAccountId);
      Account toAccount = accountDao.findAccount(toAccountId);
      assert amount > 0;
      double newBalance = fromAccount.getBalance() - amount;
      switch (fromAccount.getOverdraftPolicy()) {
      case Account.NEVER:
        if (newBalance < 0)
          throw new MoneyTransferException("Insufficient funds");
        break;
      case Account.ALLOWED:
        Calendar then = Calendar.getInstance();
        then.setTime(fromAccount.getDateOpened());
        Calendar now = Calendar.getInstance();

        double yearsOpened = now.get(Calendar.YEAR) - then.get(Calendar.YEAR);
        int monthsOpened = now.get(Calendar.MONTH) - then.get(Calendar.MONTH);
        if (monthsOpened < 0) {
          yearsOpened--;
          monthsOpened += 12;
        }
        yearsOpened = yearsOpened + (monthsOpened / 12.0);
        if (yearsOpened < fromAccount.getRequiredYearsOpen()
            || newBalance < fromAccount.getLimit())
          throw new MoneyTransferException("Limit exceeded");
        break;
      default:
        throw new MoneyTransferException("Unknown overdraft type: "
            + fromAccount.getOverdraftPolicy());

      }
      fromAccount.setBalance(newBalance);
      toAccount.setBalance(toAccount.getBalance() + amount);

      accountDao.saveAccount(fromAccount);
      accountDao.saveAccount(toAccount);

      TransferTransaction txn = new TransferTransaction(fromAccount, toAccount,
          amount, new Date());
      bankingTransactionDao.addTransaction(txn);

      transactionManager.commit();

      logger.debug("Leaving AccountServiceProceduralImpl.transfer()");
      return txn;
    } catch (MoneyTransferException e) {
      logger.debug(
          "Exception thrown in AccountServiceProceduralImpl.transfer()", e);
      transactionManager.commit();
      throw e;
    } catch (RuntimeException e) {
      logger.debug(
          "Exception thrown in AccountServiceProceduralImpl.transfer()", e);
      throw e;
    } finally {
      transactionManager.rollbackIfNecessary();
    }
  }

  public void create(Account account) {
    BankingSecurityManager.verifyCallerAuthorized(AccountService.class,
        "create");

    logger.debug("Entering AccountServiceProceduralImpl.create()");

    transactionManager.begin();

    auditingManager.audit(AccountService.class, "create",
        new Object[] { account.getAccountId() });

    try {
      accountDao.addAccount(account);

      transactionManager.commit();

      logger.debug("Leaving AccountServiceProceduralImpl.create()");
    } catch (RuntimeException e) {
      logger.debug("Exception thrown in AccountServiceProceduralImpl.create()",
          e);
      throw e;
    } finally {
      transactionManager.rollbackIfNecessary();
    }

  }

}
