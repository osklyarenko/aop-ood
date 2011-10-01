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
 
package net.chrisrichardson.bankingExample.domain.hibernate;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;
import static org.easymock.classextension.EasyMock.replay;
import static org.junit.Assert.assertSame;

import java.util.Collections;

import net.chrisrichardson.bankingExample.domain.Account;
import net.chrisrichardson.bankingExample.domain.AccountMother;

import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.orm.hibernate3.HibernateTemplate;


public class HibernateAccountRepositoryMockTests {

    private HibernateTemplate hibernateTemplate;
    private HibernateAccountRepository repository;
    private SessionFactory sessionFactory;

    @Before
    public void setUp() throws Exception {
        sessionFactory = createMock(SessionFactory.class);
        hibernateTemplate = createMock(HibernateTemplate.class);
        repository = new HibernateAccountRepository(sessionFactory);
        repository.setHibernateTemplate(hibernateTemplate);
    }

    @Test
    public void testRepository() {
        Account account = AccountMother.makeAccountWithNoOverdraft(0.0);
        String accountId = account.getAccountId();
        
		expect(hibernateTemplate.findByNamedQueryAndNamedParam(
				"Account.findAccountByAccountId", "accountId",
				accountId)).andReturn(Collections.singletonList(account));
        replay(hibernateTemplate);
        
        Account result = repository.findAccount(accountId);
        assertSame(account, result);
    }
    
    

}
