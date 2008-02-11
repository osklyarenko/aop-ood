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
import net.chrisrichardson.bankingExample.domain.AccountMother;
import net.chrisrichardson.bankingExample.domain.AccountRepository;
import net.chrisrichardson.bankingExample.domain.MoneyTransferException;
import net.chrisrichardson.bankingExample.domain.MoneyTransferService;
import net.chrisrichardson.bankingExample.domain.MoneyUtil;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

public class SpringMoneyTransferServiceTests extends
    AbstractDependencyInjectionSpringContextTests {

  private static final int TO_ACCOUNT_INITIAL_BALANCE = 30;
  private static final int FROM_ACCOUNT_INITIAL_BALANCE = 10;
  private MoneyTransferService service;
  private AccountRepository repository;
  private Account fromAccount;
  private Account toAccount;
  private String fromAccountId;
  private String toAccountId;

  @Override
  protected String[] getConfigLocations() {
    return new String[] { "classpath:/appCtx/*.xml" };
  }

  public void setRepository(AccountRepository repository) {
    this.repository = repository;
  }

  public void setService(MoneyTransferService service) {
    this.service = service;
  }

  private void assertBalance(double expectedBalance, String accountId) {
    MoneyUtil.assertMoneyEquals(expectedBalance, repository.findAccount(
        accountId).getBalance());
  }

  @Override
  protected void onSetUp() throws Exception {
    super.onSetUp();
    fromAccount = AccountMother
        .makeAccountWithNoOverdraft(FROM_ACCOUNT_INITIAL_BALANCE);
    toAccount = AccountMother
        .makeAccountWithNoOverdraft(TO_ACCOUNT_INITIAL_BALANCE);
    fromAccountId = fromAccount.getAccountId();
    toAccountId = toAccount.getAccountId();
    repository.addAccount(fromAccount);
    repository.addAccount(toAccount);
  }

  public void testTransfer() {
    service.transfer(fromAccountId, toAccountId, 5);
    assertBalance(FROM_ACCOUNT_INITIAL_BALANCE - 5, fromAccountId);
    assertBalance(TO_ACCOUNT_INITIAL_BALANCE + 5, toAccountId);
  }

  public void testTransfer_zero() {

    service.transfer(fromAccountId, toAccountId, FROM_ACCOUNT_INITIAL_BALANCE);

    assertBalance(0, fromAccountId);
    assertBalance(TO_ACCOUNT_INITIAL_BALANCE + FROM_ACCOUNT_INITIAL_BALANCE,
        toAccountId);
  }

  public void testTransfer_overdrawn() {
    try {
      service.transfer(fromAccountId, toAccountId,
          FROM_ACCOUNT_INITIAL_BALANCE + 5);
      fail("Expected exception");
    } catch (MoneyTransferException e) {

    }

    assertBalance(FROM_ACCOUNT_INITIAL_BALANCE, fromAccountId);
    assertBalance(TO_ACCOUNT_INITIAL_BALANCE, toAccountId);
  }

  public void testTransfer_ok() {
    Account savingsAccount = AccountMother.makeAccountWithNoOverdraft(150);
    Account checkingAccount = AccountMother.makeAccountWithNoOverdraft(10);
    String savingsAccountId = savingsAccount.getAccountId();
    String checkingAccountId = checkingAccount.getAccountId();
    repository.addAccount(savingsAccount);
    repository.addAccount(checkingAccount);
    
    service.transfer(savingsAccountId, checkingAccountId, 50);
    // FIXME check return value
    
    assertBalance(100, savingsAccountId);
    assertBalance(60, checkingAccountId);

  }

}
