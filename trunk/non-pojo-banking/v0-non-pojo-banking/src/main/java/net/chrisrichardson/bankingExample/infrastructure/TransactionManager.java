package net.chrisrichardson.bankingExample.infrastructure;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {

  private static TransactionManager theInstance = new TransactionManager();

  private TransactionManager() {
  }

  public static TransactionManager getInstance() {
    return theInstance;
  }

  public void begin() {
    JdbcConnectionManager.getInstance().getConnectionForTransaction();
  }

  public void commit() {
    try {
      Connection con = JdbcConnectionManager.getInstance().getConnection();
      con.commit();
      con.close();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    JdbcConnectionManager.getInstance().releaseConnectionForTransaction();
  }

  public void rollbackIfNecessary() {
    final Connection con = JdbcConnectionManager.getInstance().getConnection();
    if (con != null) {
      try {
        con.rollback();
        con.close();
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
      JdbcConnectionManager.getInstance().releaseConnectionForTransaction();
    }
  }

}
