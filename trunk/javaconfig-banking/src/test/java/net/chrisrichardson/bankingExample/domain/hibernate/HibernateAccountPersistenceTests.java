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
import net.chrisrichardson.bankingExample_.config.BankingConfig;

import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=BankingConfig.class, loader=AnnotationConfigContextLoader.class)
public class HibernateAccountPersistenceTests {

  private HibernateTemplate hibernateTemplate;

  @Autowired
  public void setSessionFactory(SessionFactory sessionFactory) {
    this.hibernateTemplate = new HibernateTemplate(sessionFactory);
  }
  
  @Test
	public void testSimple() {
		Account account = AccountMother.makeAccountWithNoOverdraft(1.0);
		hibernateTemplate.save(account);
		
		Account account2 = hibernateTemplate.get(Account.class, account.getId());
		Assert.assertNotNull(account2);
	}

}
