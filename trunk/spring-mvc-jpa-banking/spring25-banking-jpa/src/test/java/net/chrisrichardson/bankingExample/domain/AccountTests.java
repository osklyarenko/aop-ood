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

import junit.framework.*;

public class AccountTests extends TestCase {

    private Account account;

    public void setUp() {
        account = AccountMother.makeAccountWithNoOverdraft(10.0);
    }

	public void testNoOverdraft() throws MoneyTransferException {
        MoneyUtil.assertMoneyEquals(10.0, account.getBalance());
        account.debit(6);
        MoneyUtil.assertMoneyEquals(4.0, account.getBalance());
        account.credit(10);
        MoneyUtil.assertMoneyEquals(14.0, account.getBalance());
        account.debit(14);
        MoneyUtil.assertMoneyEquals(0, account.getBalance());
    }

    public void testNoOverdraftAllowed() {
        MoneyUtil.assertMoneyEquals(10.0, account.getBalance());
        try {
            account.debit(20);
            fail("exception expected");
        } catch (MoneyTransferException e) {

        }
    }
    
    public void testGetYearsOpen() {
      account = AccountMother.makeAccount(10, new NoOverdraftAllowed(), 1.5);
      MoneyUtil.assertMoneyEquals(1.5, account.getYearsOpen());
    }
}
