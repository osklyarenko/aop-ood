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

import java.util.List;

import net.chrisrichardson.bankingExample.domain.Account;
import net.chrisrichardson.bankingExample.domain.AccountRepository;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class HibernateAccountRepository implements AccountRepository {

	private HibernateTemplate hibernateTemplate;

	public HibernateAccountRepository(HibernateTemplate template) {
		hibernateTemplate = template;
	}

	public void addAccount(Account account) {
		hibernateTemplate.save(account);
	}

	public Account findAccount(final String accountId) {
		return (Account) DataAccessUtils.uniqueResult(hibernateTemplate
				.findByNamedQueryAndNamedParam(
						"Account.findAccountByAccountId", "accountId",
						accountId));
	}

	public List<Account> findAccounts() {
		return hibernateTemplate.find("from " + Account.class.getSimpleName());
	}

}
