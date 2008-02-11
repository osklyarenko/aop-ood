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

    public static final int NEVER = 1;
    public static final int ALLOWED = 2;
    
    private int overdraftPolicy;

    private String accountId;

    private Date dateOpened;

    private double requiredYearsOpen;
    private double limit;
    
    Account() {
    }
   
    public int getId() {
      return id;
    }
    public Account(int id, String accountId, double balance, int overdraftPolicy, Date dateOpened, double requiredYearsOpen, double limit) {
    	this.id = id;
    	this.balance = balance;
    	this.overdraftPolicy = overdraftPolicy;
    	this.accountId = accountId;
    	this.dateOpened = dateOpened;
    	this.requiredYearsOpen = requiredYearsOpen;
    	this.limit = limit;
    }

    public Account(String accountId, double balance, int overdraftPolicy, Date dateOpened, double requiredYearsOpen, double limit) {
      this(0, accountId, balance, overdraftPolicy, dateOpened, requiredYearsOpen, limit);
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

	public int getOverdraftPolicy() {
		return overdraftPolicy;
	}
  
   public Date getDateOpened() {
    return dateOpened;
  }
   
   public double getRequiredYearsOpen() {
    return requiredYearsOpen;
  }

  public double getLimit() {
    return limit;
  }
   
}
