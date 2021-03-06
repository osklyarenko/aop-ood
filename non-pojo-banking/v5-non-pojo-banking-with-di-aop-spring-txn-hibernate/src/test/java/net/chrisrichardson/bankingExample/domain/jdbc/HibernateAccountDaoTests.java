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
 


package net.chrisrichardson.bankingExample.domain.jdbc;

import junit.framework.TestCase;
import net.chrisrichardson.bankingExample.domain.Account;
import net.chrisrichardson.bankingExample.domain.AccountMother;
import net.chrisrichardson.bankingExample.domain.hibernate.HibernateAccountDao;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HibernateAccountDaoTests extends TestCase {

  private HibernateAccountDao dao;

  @Override
  protected void setUp() throws Exception {
    ApplicationContext ctx = new ClassPathXmlApplicationContext(
        "appCtx/*.xml");
    this.dao = (HibernateAccountDao) ctx.getBean("hibernateAccountDao");
  }

  public void test() throws Exception {

    Account account = AccountMother.makeNoOverdraftAllowedAccount(0);
    String accountId = account.getAccountId();

    dao.addAccount(account);

    assertNotNull(account);
    Account account2 = dao.findAccount(accountId);
    assertNotNull(account2);
  }

}
