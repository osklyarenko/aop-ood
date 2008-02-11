package net.chrisrichardson.bankingExample.infrastructure;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TransactionManager {

  private Log logger = LogFactory.getLog(getClass());
  
	private final JdbcConnectionManager jdbcConnectionManager;

  public TransactionManager(JdbcConnectionManager jdbcConnectionManager) {
    this.jdbcConnectionManager = jdbcConnectionManager;
  }
	
	public void begin() {
	  logger.debug("begin transaction");
		jdbcConnectionManager.getConnectionForTransaction();
	}

	public void commit() {
    logger.debug("commit transaction");
		try {
			Connection con = jdbcConnectionManager.getConnection();
			con.commit();
			con.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		jdbcConnectionManager.releaseConnectionForTransaction();
	}

	public void rollbackIfNecessary() {
		final Connection con = jdbcConnectionManager.getExistingConnection();
		if (con != null) {
			try {
		    logger.debug("rollback transaction");
				con.rollback();
				con.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
			jdbcConnectionManager.releaseConnectionForTransaction();
		}
	}

}
