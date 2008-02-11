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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;

import net.chrisrichardson.bankingExample.domain.Account;
import net.chrisrichardson.bankingExample.domain.AccountDao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;

public class JdbcAccountDao implements AccountDao {

  private Log logger = LogFactory.getLog(getClass());

  private JdbcTemplate jdbcTemplate;

  public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public void addAccount(Account account) {
    logger.debug("adding account");
    int count = jdbcTemplate
        .update(
            "INSERT INTO BANK_ACCOUNT(accountId, BALANCE, overdraftPolicy, dateOpened, requiredYearsOpen, limit) values(?, ?, ?, ?, ?, ?)",
            new Object[] { account.getAccountId(), account.getBalance(),
                account.getOverdraftPolicy(),
                new Timestamp(account.getDateOpened().getTime()),
                account.getRequiredYearsOpen(), account.getLimit() },
            new int[] { Types.VARCHAR, Types.DOUBLE, Types.INTEGER,
                Types.TIMESTAMP, Types.DOUBLE, Types.DOUBLE });
    Assert.isTrue(count == 1);
  }

  public Account findAccount(String accountId) {
    logger.debug("finding account");
    return (Account) jdbcTemplate.queryForObject(
        "SELECT * FROM BANK_ACCOUNT WHERE accountId  = ?",
        new Object[] { accountId }, new RowMapper() {

          public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Account(rs.getInt("ACCOUNT_ID"), rs
                .getString("accountId"), rs.getDouble("BALANCE"), rs
                .getInt("overdraftPolicy"), new Date(rs.getTimestamp(
                "dateOpened").getTime()), rs.getDouble("requiredYearsOpen"), rs
                .getDouble("limit"));

          }
        });

  }

  public void saveAccount(Account account) {
    int count = jdbcTemplate
        .update(
            "UPDATE BANK_ACCOUNT set accountId = ?, BALANCE = ?, overdraftPolicy = ?, dateOpened = ?, requiredYearsOpen = ?, limit = ? WHERE ACCOUNT_ID = ?",
            new Object[] { account.getAccountId(), account.getBalance(),
                account.getOverdraftPolicy(),
                new Timestamp(account.getDateOpened().getTime()),
                account.getRequiredYearsOpen(), account.getLimit(),
                account.getId() }, new int[] { Types.VARCHAR, Types.DOUBLE,
                Types.INTEGER, Types.TIMESTAMP, Types.DOUBLE, Types.DOUBLE,
                Types.INTEGER });
    Assert.isTrue(count == 1);

  }

}
