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
import net.chrisrichardson.bankingExample.domain.AccountDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateAccountDao extends HibernateDaoSupport implements AccountDao {

  @Autowired
  public HibernateAccountDao(HibernateTemplate template) {
    setHibernateTemplate(template);
  }

  public void addAccount(Account account) {
    getHibernateTemplate().save(account);
  }

  @Override
  public Account findAccount(final String accountId) {
    return (Account) DataAccessUtils.uniqueResult(getHibernateTemplate()
        .findByNamedQueryAndNamedParam("Account.findAccountByAccountId",
            "accountId", accountId));
  }

  @Override
  public Account findAccountWithOverdraftPolicy(String accountId) {
    return (Account) DataAccessUtils.uniqueResult(getHibernateTemplate()
        .findByNamedQueryAndNamedParam("Account.findAccountByAccountIdWithOverdraftPolicy",
            "accountId", accountId));
  }

}
