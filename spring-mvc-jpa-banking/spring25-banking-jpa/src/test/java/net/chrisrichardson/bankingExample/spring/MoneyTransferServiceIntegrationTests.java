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
import net.chrisrichardson.bankingExample.domain.MoneyTransferService;
import net.chrisrichardson.bankingExample.domain.MoneyUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit38.AbstractJUnit38SpringContextTests;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

@ContextConfiguration(locations = { "classpath:/appCtx/*.xml" })
public class MoneyTransferServiceIntegrationTests extends
		AbstractJUnit38SpringContextTests {

	@Autowired
	private MoneyTransferService service;

	@Autowired
	private AccountRepository repository;

	@Autowired
	private TransactionTemplate transactionTemplate;

	private void assertBalance(final double expectedBalance, final String accountId) {
		transactionTemplate.execute(new TransactionCallback(){

			public Object doInTransaction(TransactionStatus status) {
				MoneyUtil.assertMoneyEquals(expectedBalance, repository.findAccount(
						accountId).getBalance());
				return null;
			}});
	}

	public void testTransfer() throws Exception {
		double fromAccountInitialBalance = 10;
		double toAccountInitialBalance = 20;

		final Account fromAccount = AccountMother
				.makeAccountWithNoOverdraft(fromAccountInitialBalance);
		final Account toAccount = AccountMother
				.makeAccountWithNoOverdraft(toAccountInitialBalance);
		String fromAccountId = fromAccount.getAccountId();
		String toAccountId = toAccount.getAccountId();

		transactionTemplate.execute(new TransactionCallback(){

			public Object doInTransaction(TransactionStatus status) {
				repository.addAccount(fromAccount);
				repository.addAccount(toAccount);
				return null;
			}});

		
		service.transfer(fromAccountId, toAccountId, 5);

		assertBalance(fromAccountInitialBalance - 5, fromAccountId);
		assertBalance(toAccountInitialBalance + 5, toAccountId);
	}

}
