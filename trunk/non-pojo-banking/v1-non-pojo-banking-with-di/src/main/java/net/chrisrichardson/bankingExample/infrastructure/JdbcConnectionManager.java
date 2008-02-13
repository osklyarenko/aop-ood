package net.chrisrichardson.bankingExample.infrastructure;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JdbcConnectionManager {

  private static String[] ddl = {
      "create table BANKING_TRANSACTION (id integer generated by default as identity (start with 1), fromAccount integer, toAccount integer, amount double, date timestamp, primary key (id))",
      "create table BANK_ACCOUNT (ACCOUNT_ID integer generated by default as identity (start with 1), BALANCE double, accountId varchar(255), overdraftPolicy integer, dateOpened timestamp, requiredYearsOpen double, limit double, primary key (ACCOUNT_ID))",
      "alter table BANKING_TRANSACTION add constraint FKAD190052A8B6B92 foreign key (toAccount) references BANK_ACCOUNT",
      "alter table BANKING_TRANSACTION add constraint FKAD1900591467F83 foreign key (fromAccount) references BANK_ACCOUNT" };

  private Log logger = LogFactory.getLog(JdbcConnectionManager.class);
  private DataSource dataSource;
  private ThreadLocal<Connection> connectionHolder = new ThreadLocal<Connection>();

  public JdbcConnectionManager(DataSource dataSource) {
    this.dataSource = dataSource;
    if (!schemaExists())
      createSchema();
  }

  private boolean schemaExists() {
    Connection con = getConnection();
    try {
      Statement s = con.createStatement();
      s.executeQuery("SELECT * FROM BANKING_TRANSACTION");
      return true;
    } catch (Exception e) {
      return false;
    } finally {
      closeConnection(con);
    }
  }

  private void createSchema() {
    Connection con = getConnection();
    try {
      Statement s = con.createStatement();
      for (String statement : ddl) {
        System.out.println(statement);
        s.execute(statement);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      closeConnection(con);
    }

  }

  void getConnectionForTransaction() {
    Connection con;
    try {
      con = dataSource.getConnection();
      con.setAutoCommit(false);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    setConnection(con);
  }

  void releaseConnectionForTransaction() {
    setConnection(null);
  }

  public void setConnection(final Connection con) {
    connectionHolder.set(con);
  }

  public Connection getConnection() {
    Connection con = connectionHolder.get();
    if (con == null) {
      try {
        return dataSource.getConnection();
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    } else
      return con;
  }

  public void cleanUp(Connection con, PreparedStatement ps, ResultSet rs) {
    closeResultSet(rs);
    closePreparedStatement(ps);
    closeConnection(con);

  }

  private void closeConnection(Connection con) {
    if (con != null) {
      if (getConnection() == null) {
        try {
          con.close();
        } catch (SQLException e) {
          logger.warn("Couldn't close con", e);
        }
      }
    }
  }

  private void closePreparedStatement(PreparedStatement ps) {
    if (ps != null) {
      try {
        ps.close();
      } catch (SQLException e) {
        logger.warn("Couldn't close ps", e);
      }
    }
  }

  private void closeResultSet(ResultSet rs) {
    if (rs != null) {
      try {
        rs.close();
      } catch (SQLException e) {
        logger.warn("Couldn't close rs", e);
      }
    }
  }

  public void cleanUp(Connection con, PreparedStatement ps) {
    cleanUp(con, ps, null);
  }

}