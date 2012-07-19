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

package net.chrisrichardson.bankingExample.domain;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MoneyTransferServiceImpl implements MoneyTransferService {

	private final AccountRepository accountRepository;

	private final BankingTransactionRepository bankingTransactionRepository;

	@Autowired
	public MoneyTransferServiceImpl(AccountRepository accountRepository,
			BankingTransactionRepository bankingTransactionRepository) {
		this.accountRepository = accountRepository;
		this.bankingTransactionRepository = bankingTransactionRepository;
	}

	@Override
	public BankingTransaction transfer(String fromAccountId,
			String toAccountId, double amount) throws MoneyTransferException {
		Account fromAccount = accountRepository.findAccount(fromAccountId);
		Account toAccount = accountRepository.findAccount(toAccountId);
		fromAccount.debit(amount);
		toAccount.credit(amount);
		TransferTransaction txn = new TransferTransaction(fromAccount,
				toAccount, amount, new Date());
		bankingTransactionRepository.addTransaction(txn);
		return txn;
	}

	@Override
	public List<Account> findAccounts() {
		return accountRepository.findAccounts();
	}

	@Override
  public Account addAccount(String accountId, double balance,
      OverdraftPolicy overdraftPolicy) {
    Account account = new Account(accountId, balance, overdraftPolicy);
    accountRepository.addAccount(account);
    return account;
  }

  @Override
  public Account findAccountByid(String accountId) {
    return accountRepository.findAccount(accountId);
  }

}
