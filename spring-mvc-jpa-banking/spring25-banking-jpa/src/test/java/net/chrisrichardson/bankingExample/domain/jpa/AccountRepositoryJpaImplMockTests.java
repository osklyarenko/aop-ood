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

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;
import static org.easymock.classextension.EasyMock.replay;
import static org.easymock.classextension.EasyMock.verify;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import junit.framework.TestCase;
import net.chrisrichardson.bankingExample.domain.Account;
import net.chrisrichardson.bankingExample.domain.AccountMother;

public class AccountRepositoryJpaImplMockTests extends TestCase {

	private EntityManager entityManager;
	private AccountRepositoryJpaImpl repository;
	private Query query;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		entityManager = createMock(EntityManager.class);
		repository = new AccountRepositoryJpaImpl();
		repository.setEntityManager(entityManager);
		query = createMock(Query.class);
	}

	public void testRepository() {
		Account account = AccountMother.makeAccountWithNoOverdraft(0.0);
		String accountId = account.getAccountId();

		expect(entityManager.createNamedQuery("Account.findAccountByAccountId"))
				.andReturn(query);
		expect(query.setParameter("accountId", accountId)).andReturn(query);
		expect(query.getSingleResult()).andReturn(account);
		
		replay(entityManager, query);

		Account result = repository.findAccount(accountId);
		assertSame(account, result);
		
		
		verify(entityManager, query);
	}

}
