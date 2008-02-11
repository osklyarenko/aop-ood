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

import java.io.Serializable;
import java.util.Date;

public class Account implements Serializable {

	private int id;

	private double balance;

	private OverdraftPolicy overdraftPolicy;

	private String accountId;

	private CalendarDate dateOpened;

	Account() {
	}

	public Account(String accountId, double initialBalance, OverdraftPolicy overdraftPolicy) {
		this.accountId = accountId;
		this.balance = initialBalance;
		this.overdraftPolicy = overdraftPolicy;
		this.dateOpened = new CalendarDate(new Date());
	}

  public Account(String accountId, double initialBalance, OverdraftPolicy overdraftPolicy, Date dateOpened) {
    this.accountId = accountId;
    this.balance = initialBalance;
    this.overdraftPolicy = overdraftPolicy;
    this.dateOpened = new CalendarDate(dateOpened);
  }

  public int getId() {
		return id;
	}

	public void debit(double amount) throws MoneyTransferException {
		assert amount > 0;
		double originalBalance = balance;
		double newBalance = balance - amount;
		overdraftPolicy.beforeDebitCheck(this, originalBalance, newBalance);
		balance = newBalance;
		overdraftPolicy.afterDebitAction(this, originalBalance, newBalance);
	}

	public void credit(double amount) {
		assert amount > 0;
		balance += amount;
	}

	public double getBalance() {
		return balance;
	}

	public String getAccountId() {
		return accountId;
	}

	public double getYearsOpen() {
		return dateOpened.getYearsOpen();
	}

}
