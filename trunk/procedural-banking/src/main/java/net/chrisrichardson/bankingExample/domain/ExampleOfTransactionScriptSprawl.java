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

public class ExampleOfTransactionScriptSprawl implements MoneyTransferService {

	private final AccountRepository accountDAO;

	private final BankingTransactionRepository bankingTransactionDAO;

	public ExampleOfTransactionScriptSprawl(AccountRepository accountDAO,
			BankingTransactionRepository bankingTransactionDAO) {
		this.accountDAO = accountDAO;
		this.bankingTransactionDAO = bankingTransactionDAO;
	}

	// Original version
	
	public void transfer_V0(String fromAccountId, String toAccountId,
			double amount) {
		Account fromAccount = accountDAO.findAccount(fromAccountId);
		Account toAccount = accountDAO.findAccount(toAccountId);
		assert amount > 0;
		fromAccount.setBalance(fromAccount.getBalance() - amount);
		toAccount.setBalance(toAccount.getBalance() + amount);
	}

	// +recording

	public TransferTransaction transfer_V1(String fromAccountId,
			String toAccountId, double amount) {
		Account fromAccount = accountDAO.findAccount(fromAccountId);
		Account toAccount = accountDAO.findAccount(toAccountId);
		assert amount > 0;
		fromAccount.setBalance(fromAccount.getBalance() - amount);
		toAccount.setBalance(toAccount.getBalance() + amount);
		TransferTransaction txn = new TransferTransaction(fromAccount,
				toAccount, amount, new Date());
		bankingTransactionDAO.addTransaction(txn);
		return txn;
	}

	// + simple kind of overdraft check
	
	public BankingTransaction transfer_V2(String fromAccountId,
			String toAccountId, double amount) throws MoneyTransferException {
		Account fromAccount = accountDAO.findAccount(fromAccountId);
		Account toAccount = accountDAO.findAccount(toAccountId);
		assert amount > 0;
		double newBalance = fromAccount.getBalance() - amount;
		if (newBalance < 0)
			throw new MoneyTransferException();
		fromAccount.setBalance(newBalance);
		toAccount.setBalance(toAccount.getBalance() + amount);
		TransferTransaction txn = new TransferTransaction(fromAccount,
				toAccount, amount, new Date());
		bankingTransactionDAO.addTransaction(txn);
		return txn;
	}

	// + multiple kind of overdraft check

	public BankingTransaction transfer(String fromAccountId,
			String toAccountId, double amount) throws MoneyTransferException {
		Account fromAccount = accountDAO.findAccount(fromAccountId);
		Account toAccount = accountDAO.findAccount(toAccountId);
		assert amount > 0;
		double originalBalance = fromAccount.getBalance();
		double newBalance = fromAccount.getBalance() - amount;
		beforeDebitCheck(fromAccount, originalBalance, newBalance);
		fromAccount.setBalance(newBalance);
		afterDebitCheck(fromAccount, originalBalance, newBalance);
		toAccount.setBalance(toAccount.getBalance() + amount);
		TransferTransaction txn = new TransferTransaction(fromAccount,
				toAccount, amount, new Date());
		bankingTransactionDAO.addTransaction(txn);
		return txn;
	}

	private void beforeDebitCheck(Account fromAccount, double originalBalance,
			double newBalance) throws MoneyTransferException {
		switch (fromAccount.getOverdraftPolicy()) {
		case Account.NEVER:
			if (newBalance < 0)
				throw new MoneyTransferException();
		case Account.ALLOWED:
			break;
		default:
			throw new MoneyTransferException("Unknown overdraft type: "
					+ fromAccount.getOverdraftPolicy());

		}

	}

	private void afterDebitCheck(Account fromAccount, double originalBalance,
			double newBalance) throws MoneyTransferException {
		switch (fromAccount.getOverdraftPolicy()) {
		case Account.ALLOWED:
			// TODO Do something
			break;
		default:
		}
	}
}
