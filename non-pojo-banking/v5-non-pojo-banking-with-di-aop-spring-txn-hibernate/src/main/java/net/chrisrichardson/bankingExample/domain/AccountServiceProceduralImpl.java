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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceProceduralImpl implements
    AccountService {

  private final AccountDao accountDao;

  private final BankingTransactionDao bankingTransactionDao;


  @Autowired
  public AccountServiceProceduralImpl(
      AccountDao accountDao,
      BankingTransactionDao bankingTransactionDao) {
    this.accountDao = accountDao;
    this.bankingTransactionDao = bankingTransactionDao;
  }

  public BankingTransaction transfer(String fromAccountId, String toAccountId,
      double amount)  {

    Account fromAccount = accountDao.findAccountWithOverdraftPolicy(fromAccountId);
    Account toAccount = accountDao.findAccount(toAccountId);
    assert amount > 0;
    double newBalance = fromAccount.getBalance() - amount;
    switch (fromAccount.getOverdraftPolicy().getOverdraftPolicyType()) {
    case OverdraftPolicy.NEVER:
      if (newBalance < 0)
        throw new MoneyTransferException("Insufficient funds");
      break;
    case OverdraftPolicy.ALLOWED:
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
      if (yearsOpened < fromAccount.getOverdraftPolicy().getRequiredYearsOpen()
          || newBalance < fromAccount.getOverdraftPolicy().getLimit())
        throw new MoneyTransferException("Limit exceeded");
      break;
    default:
      throw new MoneyTransferException("Unknown overdraft type: "
          + fromAccount.getOverdraftPolicy().getOverdraftPolicyType());

    }
    fromAccount.setBalance(newBalance);
    toAccount.setBalance(toAccount.getBalance() + amount);

    TransferTransaction txn = new TransferTransaction(fromAccount, toAccount,
        amount, new Date());
    bankingTransactionDao.addTransaction(txn);

    return txn; 
  }

  public void create(Account account) {
    accountDao.addAccount(account);
  }

}
