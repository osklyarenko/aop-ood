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

import junit.framework.TestCase;

public class AccountTests extends TestCase {

  private Account account;

  public void setUp() {
    account = new Account("ABC", 10.0, new NoOverdraftAllowed());
  }

  public void testInitialBalance() {
    MoneyUtil.assertMoneyEquals(10.0, account.getBalance());
  }

  public void testDebit() {
    account.debit(6);
    MoneyUtil.assertMoneyEquals(4.0, account.getBalance());
  }

  public void testCredit() {
    account.credit(9);
    MoneyUtil.assertMoneyEquals(19.0, account.getBalance());
  }

  public void testDebitToZero() {
    account.debit(account.getBalance());
    MoneyUtil.assertMoneyEquals(0.0, account.getBalance());
  }

  public void testDebitNoOverdraftAllowed() {
    try {
      account.debit(20);
      fail("exception expected");
    } catch (MoneyTransferException e) {
    }
    MoneyUtil.assertMoneyEquals(10.0, account.getBalance());
  }

  public void testGetYearsOpen() {
    account = AccountMother.makeAccount(10, new NoOverdraftAllowed(), 1.5);
    assertEquals(1.5, account.getYearsOpen(), 0.0001);
  }
}
