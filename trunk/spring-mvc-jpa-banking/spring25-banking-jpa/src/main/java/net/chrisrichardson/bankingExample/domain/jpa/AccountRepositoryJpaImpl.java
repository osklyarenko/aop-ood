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

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.chrisrichardson.bankingExample.domain.Account;
import net.chrisrichardson.bankingExample.domain.AccountRepository;

import org.springframework.stereotype.Repository;

@Repository
public class AccountRepositoryJpaImpl implements AccountRepository {

	private EntityManager entityManager;

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void addAccount(Account account) {
		entityManager.persist(account);
	}

	public Account findAccount(final String accountId) {
		return (Account) entityManager.createNamedQuery(
				"Account.findAccountByAccountId").setParameter("accountId",
				accountId).getSingleResult();
	}

	public List<Account> findAccounts() {
		return entityManager.createNamedQuery("Account.findAll").getResultList();
	}

	@PostConstruct
	public void postConstruct() {
		System.out.println("hello");
	}
	
	@PreDestroy
	public void predestroy() {
		System.out.println("goodbte");
	}}
