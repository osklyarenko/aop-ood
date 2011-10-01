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

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

public class MoneyTransferServiceTests {

  private MoneyTransferService service;

  private AccountRepository accountRepository;

  private BankingTransactionRepository bankingTransactionRepository;

  private Account fromAccount;

  private Account toAccount;

  private String fromAccountId;

  private String toAccountId;

  @Before
  public void setUp() throws Exception {
    accountRepository = createMock(AccountRepository.class);
    bankingTransactionRepository = createMock(BankingTransactionRepository.class);
    service = new MoneyTransferServiceImpl(accountRepository,
        bankingTransactionRepository);

    fromAccount = AccountMother.makeAccountWithNoOverdraft(100);
    toAccount = AccountMother.makeAccountWithNoOverdraft(200);
    fromAccountId = fromAccount.getAccountId();
    toAccountId = toAccount.getAccountId();

  }

  private void replayMocks() {
    replay(accountRepository, bankingTransactionRepository);
  }

  private void verifyMocks() {
    verify(accountRepository, bankingTransactionRepository);
  }

  @Test
  public void testTransfer_normal() throws Exception {

    expect(accountRepository.findAccount(fromAccountId)).andReturn(fromAccount);
    expect(accountRepository.findAccount(toAccountId)).andReturn(toAccount);

    bankingTransactionRepository.addTransaction(isA(BankingTransaction.class));

    replayMocks();

    BankingTransaction result = service
        .transfer(fromAccountId, toAccountId, 30);

    assertNotNull(result);

    MoneyUtil.assertMoneyEquals(70.0, fromAccount.getBalance());
    MoneyUtil.assertMoneyEquals(230.0, toAccount.getBalance());

    verifyMocks();

  }


  @Test
  public void testTransfer_overdrawn() throws Exception {

    expect(accountRepository.findAccount(fromAccountId)).andReturn(fromAccount);
    expect(accountRepository.findAccount(toAccountId)).andReturn(toAccount);

    replayMocks();

    try {
      service.transfer(fromAccountId, toAccountId, 150);
      fail();
    } catch (MoneyTransferException e) {
    }

    verifyMocks();
  }
}
