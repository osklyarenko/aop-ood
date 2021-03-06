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

public class Account {

    private int id;
    private double balance;
    private String accountId;

    private Date dateOpened;
    private OverdraftPolicy overdraftPolicy;

    Account() {
    }
   
    public int getId() {
      return id;
    }
 
    public Account(String accountId, double balance, Date dateOpened, OverdraftPolicy overdraftPolicy) {
      this.id = 0;
      this.balance = balance;
      this.accountId = accountId;
      this.dateOpened = dateOpened;
      this.overdraftPolicy = overdraftPolicy;
    }

    public String getAccountId() {
        return accountId;
    }

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getBalance() {
		return balance;
	}

   public Date getDateOpened() {
    return dateOpened;
  }
   
   public OverdraftPolicy getOverdraftPolicy() {
    return overdraftPolicy;
  }
   
   
}
