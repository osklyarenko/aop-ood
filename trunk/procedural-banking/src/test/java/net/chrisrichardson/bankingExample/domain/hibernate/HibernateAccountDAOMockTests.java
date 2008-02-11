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

import net.chrisrichardson.bankingExample.domain.Account;
import net.chrisrichardson.bankingExample.domain.AccountMother;

import org.jmock.cglib.Mock;
import org.jmock.cglib.MockObjectTestCase;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class HibernateAccountDAOMockTests extends MockObjectTestCase {

    private Mock mockHibernateTemplate;
    private HibernateTemplate hibernateTemplate;
    private HibernateAccountRepository repository;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mockHibernateTemplate = new Mock(HibernateTemplate.class);
        hibernateTemplate = (HibernateTemplate)mockHibernateTemplate.proxy();
        repository = new HibernateAccountRepository(hibernateTemplate);
    }
    
    public void testDAO() {
        Account account = AccountMother.makeNoOverdraftAllowedAccount(10);
        mockHibernateTemplate.expects(once()).method("execute").will(returnValue(account));
        Account result = repository.findAccount("accountId");
        assertSame(account, result);
    }
    
    

}
