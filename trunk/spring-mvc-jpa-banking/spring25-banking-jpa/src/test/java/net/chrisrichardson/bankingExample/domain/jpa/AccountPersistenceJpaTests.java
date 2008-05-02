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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.chrisrichardson.bankingExample.domain.Account;
import net.chrisrichardson.bankingExample.domain.AccountMother;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit38.AbstractJUnit38SpringContextTests;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

@ContextConfiguration(locations = { "classpath:/appCtx/banking-entity-manager-factory.xml", 
		"/appCtx/banking-datasource.xml",
		"/appCtx/banking-local-transaction.xml"
		})
public class AccountPersistenceJpaTests extends
		AbstractJUnit38SpringContextTests {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private TransactionTemplate transactionTemplate;
	
	public void testSimple() {
		final Account account = AccountMother.makeAccountWithNoOverdraft(1.0);
		transactionTemplate.execute(new TransactionCallback(){

			public Object doInTransaction(TransactionStatus status) {
				entityManager.persist(account);
				return null;
			}});

		Account account2 = entityManager.find(Account.class, account.getId());
		assertNotNull(account2);
	}

}
