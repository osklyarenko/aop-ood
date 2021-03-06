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
import net.chrisrichardson.bankingExample.domain.LimitedOverdraft;
import net.chrisrichardson.bankingExample.domain.NoOverdraftAllowed;
import net.chrisrichardson.bankingExample.domain.OverdraftPolicyImpl;
import net.chrisrichardson.ormunit.hibernate.HibernateMappingTests;

public class HibernateBankingExampleMappingTests extends HibernateMappingTests {

	@Override
	protected String[] getConfigLocations() {
		return BankingDomainHibernateConstants.BANK_DOMAIN_TEST_CONTEXT;
	}

	public void testAccount() {
		assertClassMapping(Account.class, "BANK_ACCOUNT");
		assertAllFieldsMapped();
	}

	public void testOverdraftPolicy() {
		assertClassMapping(OverdraftPolicyImpl.class, "OVERDRAFT_POLICY");
		assertAllFieldsMapped();
	}

	public void testLimitedOverdraft() {
		assertClassMapping(LimitedOverdraft.class, "OVERDRAFT_POLICY");
		assertAllFieldsMapped();
	}

	public void testNoOverdraftAllowed() {
		assertClassMapping(NoOverdraftAllowed.class, "OVERDRAFT_POLICY");
		assertAllFieldsMapped();
	}
}
