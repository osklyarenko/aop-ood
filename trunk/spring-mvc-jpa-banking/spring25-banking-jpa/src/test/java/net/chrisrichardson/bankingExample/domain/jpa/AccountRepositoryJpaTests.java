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

package net.chrisrichardson.bankingExample.domain.jpa;

import net.chrisrichardson.bankingExample.domain.Account;
import net.chrisrichardson.bankingExample.domain.AccountMother;
import net.chrisrichardson.bankingExample.domain.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit38.AbstractJUnit38SpringContextTests;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

@ContextConfiguration(locations = {
		"classpath:/appCtx/banking-entity-manager-factory.xml",
		"/appCtx/banking-datasource.xml",
		"/appCtx/banking-local-transaction.xml", "/appCtx/banking-service.xml" })
public class AccountRepositoryJpaTests extends
		AbstractJUnit38SpringContextTests {

	@Autowired
	private AccountRepository repository;

	@Autowired
	private TransactionTemplate transactionTemplate;

	public void test() throws Exception {
		final Account account = AccountMother.makeAccountWithNoOverdraft(10.0);
		final String accountId = account.getAccountId();

		transactionTemplate.execute(new TransactionCallback() {

			public Object doInTransaction(TransactionStatus status) {
				repository.addAccount(account);
				return null;
			}
		});

		transactionTemplate.execute(new TransactionCallback(){

			public Object doInTransaction(TransactionStatus status) {
				Account account2 = repository.findAccount(accountId);
				assertNotNull(account2);
				return null;
			}});
	}

}
