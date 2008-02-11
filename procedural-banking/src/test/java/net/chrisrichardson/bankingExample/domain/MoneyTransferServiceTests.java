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

import org.jmock.cglib.Mock;
import org.jmock.cglib.MockObjectTestCase;

public class MoneyTransferServiceTests extends MockObjectTestCase {

  private Mock mockAccountDAO;

  private Mock mockBankingTransactionDAO;

  private MoneyTransferService service;

  private Account toAccount;

  private Account fromAccount;

  static final double FROM_ACCOUNT_INITIAL_BALANCE = 1000;
  private static final double TO_ACCOUNT_INITIAL_BALANCE = 5000;
  
  protected void setUp() throws Exception {
    super.setUp();
    mockAccountDAO = new Mock(AccountRepository.class);
    mockBankingTransactionDAO = new Mock(BankingTransactionRepository.class);
    service = new MoneyTransferServiceProceduralImpl(
        (AccountRepository) mockAccountDAO.proxy(),
        (BankingTransactionRepository) mockBankingTransactionDAO.proxy());

    fromAccount = AccountMother.makeNoOverdraftAllowedAccount(FROM_ACCOUNT_INITIAL_BALANCE);
    toAccount = AccountMother.makeNoOverdraftAllowedAccount(TO_ACCOUNT_INITIAL_BALANCE);

  }

  public void testTransfer_normal() throws Exception {

    String fromAccountId = "from";
    String toAccountId = "to";
    double amount = 10.0;

    mockAccountDAO.expects(once()).method("findAccount")
        .with(eq(fromAccountId)).will(returnValue(fromAccount));

    mockAccountDAO.expects(once()).method("findAccount").with(eq(toAccountId))
        .will(returnValue(toAccount));

    mockBankingTransactionDAO.expects(once()).method("addTransaction").with(
        isA(BankingTransaction.class));

    BankingTransaction result = service.transfer(fromAccountId, toAccountId,
        amount);

    assertNotNull(result);

    assertEquals(FROM_ACCOUNT_INITIAL_BALANCE - amount, fromAccount.getBalance());
    assertEquals(TO_ACCOUNT_INITIAL_BALANCE + amount, toAccount.getBalance());
  }

  public void testTransfer_overdrawn() throws Exception {

    String fromAccountId = "from";
    String toAccountId = "to";
    double amount = FROM_ACCOUNT_INITIAL_BALANCE + 10;

    mockAccountDAO.expects(once()).method("findAccount")
        .with(eq(fromAccountId)).will(returnValue(fromAccount));

    mockAccountDAO.expects(once()).method("findAccount").with(eq(toAccountId))
        .will(returnValue(toAccount));

    try {
      service.transfer(fromAccountId, toAccountId, amount);
      fail("Exception expected");
    } catch (MoneyTransferException e) {

    }

  }

  public void testTransfer_overdraftAllowed() throws Exception {

    fromAccount = AccountMother.makeOverdraftAllowedAccount(5, 2, 100);

    String fromAccountId = "from";
    String toAccountId = "to";
    double amount = 1010.0;

    mockAccountDAO.expects(once()).method("findAccount")
        .with(eq(fromAccountId)).will(returnValue(fromAccount));

    mockAccountDAO.expects(once()).method("findAccount").with(eq(toAccountId))
        .will(returnValue(toAccount));

    mockBankingTransactionDAO.expects(once()).method("addTransaction").with(
        isA(BankingTransaction.class));

    BankingTransaction result = service.transfer(fromAccountId, toAccountId,
        amount);

    assertNotNull(result);

    assertEquals(FROM_ACCOUNT_INITIAL_BALANCE - amount, fromAccount.getBalance());
    assertEquals(TO_ACCOUNT_INITIAL_BALANCE + amount, toAccount.getBalance());

  }

}
