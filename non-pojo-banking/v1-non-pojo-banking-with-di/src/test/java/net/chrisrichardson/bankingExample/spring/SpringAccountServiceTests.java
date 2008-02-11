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

package net.chrisrichardson.bankingExample.spring;

import net.chrisrichardson.bankingExample.domain.Account;
import net.chrisrichardson.bankingExample.domain.AccountDao;
import net.chrisrichardson.bankingExample.domain.AccountMother;
import net.chrisrichardson.bankingExample.domain.AccountService;
import net.chrisrichardson.bankingExample.infrastructure.TransactionManager;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

public class SpringAccountServiceTests extends
    AbstractDependencyInjectionSpringContextTests {

  private AccountService service;
  private AccountDao dao;
  private TransactionManager transactionManager;

  public SpringAccountServiceTests() {
    setAutowireMode(AUTOWIRE_BY_NAME);
  }
  
  @Override
  protected String[] getConfigLocations() {
    return new String[] { "appCtx/banking-service.xml" };
  }

  public void setAccountServiceDelegate(AccountService service) {
    this.service = service;
  }

  public void setAccountDao(AccountDao dao) {
    this.dao = dao;
  }

  public void setTransactionManager(TransactionManager transactionManager) {
    this.transactionManager = transactionManager;
  }

  private void assertBalance(double expectedBalance, String accountId) {
    assertEquals(expectedBalance, dao.findAccount(accountId).getBalance());
  }

  public void testTransfer() throws Exception {
    double fromAccountInitialBalance = 10;
    double toAccountInitialBalance = 20;

    Account fromAccount = AccountMother
        .makeNoOverdraftAllowedAccount(fromAccountInitialBalance);
    Account toAccount = AccountMother
        .makeNoOverdraftAllowedAccount(toAccountInitialBalance);
    String fromAccountId = fromAccount.getAccountId();
    String toAccountId = toAccount.getAccountId();

    transactionManager.begin();
    dao.addAccount(fromAccount);
    dao.addAccount(toAccount);
    transactionManager.commit();

    service.transfer(fromAccountId, toAccountId, 5);

    transactionManager.begin();
    assertBalance(fromAccountInitialBalance - 5, fromAccountId);
    assertBalance(toAccountInitialBalance + 5, toAccountId);
    transactionManager.commit();
  }

}
