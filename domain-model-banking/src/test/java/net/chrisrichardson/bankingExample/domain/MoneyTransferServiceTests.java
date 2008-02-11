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

import static org.hamcrest.Matchers.is;

import org.jmock.Expectations;
import org.jmock.integration.junit3.MockObjectTestCase;

public class MoneyTransferServiceTests extends MockObjectTestCase {

  private MoneyTransferService service;

  private AccountRepository accountRepository;

  private BankingTransactionRepository bankingTransactionRepository;

  private Account fromAccount;

  private Account toAccount;

  private String fromAccountId;

  private String toAccountId;

  protected void setUp() throws Exception {
    super.setUp();
    accountRepository = mock(AccountRepository.class);
    bankingTransactionRepository = mock(BankingTransactionRepository.class);
    service = new MoneyTransferServiceImpl(accountRepository,
        bankingTransactionRepository);

    fromAccount = AccountMother.makeAccountWithNoOverdraft(100);
    toAccount = AccountMother.makeAccountWithNoOverdraft(200);
    fromAccountId = fromAccount.getAccountId();
    toAccountId = toAccount.getAccountId();

  }

  public void testTransfer_normal() throws Exception {
    checking(new Expectations() {
      {
        one(accountRepository).findAccount(fromAccountId);
        will(returnValue(fromAccount));
        one(accountRepository).findAccount(toAccountId);
        will(returnValue(toAccount));
        one(bankingTransactionRepository).addTransaction(
            with(is(BankingTransaction.class)));
      }
    });

    BankingTransaction result = service
        .transfer(fromAccountId, toAccountId, 30);

    assertNotNull(result);

    MoneyUtil.assertMoneyEquals(70.0, fromAccount.getBalance());
    MoneyUtil.assertMoneyEquals(230.0, toAccount.getBalance());

  }

  public void testTransfer_overdrawn() throws Exception {

    checking(new Expectations() {{
      one(accountRepository).findAccount(fromAccountId); will(returnValue(fromAccount));
      one(accountRepository).findAccount(toAccountId); will(returnValue(toAccount));
    }});

    try {
      service.transfer(fromAccountId, toAccountId, 150);
      fail();
    } catch (MoneyTransferException e) {
    }

  }
}
