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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import net.chrisrichardson.bankingExample.domain.Account;
import net.chrisrichardson.bankingExample.domain.AccountDao;
import net.chrisrichardson.bankingExample.infrastructure.JdbcConnectionManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository
public class JdbcAccountDao implements AccountDao {

  private JdbcConnectionManager connectionManager;

  @Autowired
  public JdbcAccountDao(JdbcConnectionManager connectionManager) {
    this.connectionManager = connectionManager;
  }
  
	public void addAccount(Account account) {
		Connection con = connectionManager.getConnection();
		PreparedStatement ps = null;
		try {
			ps = con
					.prepareStatement("INSERT INTO BANK_ACCOUNT(accountId, BALANCE, overdraftPolicy, dateOpened, requiredYearsOpen, limit) values(?, ?, ?, ?, ?, ?)");
			int index = 1;
			ps.setString(index++, account.getAccountId());
			ps.setDouble(index++, account.getBalance());
			ps.setInt(index++, account.getOverdraftPolicy());
			ps.setTimestamp(index++, new Timestamp(account.getDateOpened()
					.getTime()));
			ps.setDouble(index++, account.getRequiredYearsOpen());
			ps.setDouble(index++, account.getLimit());
			int count = ps.executeUpdate();
			Assert.isTrue(count == 1);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			connectionManager.cleanUp(con, ps);
		}
	}

	public Account findAccount(String accountId) {

		Connection con = connectionManager.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con
					.prepareStatement("SELECT * FROM BANK_ACCOUNT WHERE accountId  = ?");
			int index = 1;
			ps.setString(index++, accountId);
			rs = ps.executeQuery();
			Assert.isTrue(rs.next());
			Account account = new Account(rs.getInt("ACCOUNT_ID"), rs
					.getString("accountId"), rs.getDouble("BALANCE"), rs
					.getInt("overdraftPolicy"), new Date(rs.getTimestamp(
					"dateOpened").getTime()),
					rs.getDouble("requiredYearsOpen"), rs.getDouble("limit"));
			Assert.isTrue(!rs.next());
			return account;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			connectionManager.cleanUp(con, ps, rs);
		}

	}

	public void saveAccount(Account account) {
		{
			Connection con = connectionManager.getConnection();
			PreparedStatement ps = null;
			try {
				ps = con
						.prepareStatement("UPDATE BANK_ACCOUNT set accountId = ?, BALANCE = ?, overdraftPolicy = ?, dateOpened = ?, requiredYearsOpen = ?, limit = ? WHERE ACCOUNT_ID = ?");
				int index = 1;
				ps.setString(index++, account.getAccountId());
				ps.setDouble(index++, account.getBalance());
				ps.setInt(index++, account.getOverdraftPolicy());
				ps.setTimestamp(index++, new Timestamp(account.getDateOpened()
						.getTime()));
				ps.setDouble(index++, account.getRequiredYearsOpen());
				ps.setDouble(index++, account.getLimit());
				ps.setInt(index++, account.getId());
				int count = ps.executeUpdate();
				Assert.isTrue(count == 1, "Got: " + count);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			} finally {
				connectionManager.cleanUp(con, ps);
			}
		}
	}

}
