package net.chrisrichardson.bankingExample.infrastructure;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionManager {

	private final JdbcConnectionManager jdbcConnectionManager;

	@Autowired
  public TransactionManager(JdbcConnectionManager jdbcConnectionManager) {
    this.jdbcConnectionManager = jdbcConnectionManager;
  }
	
	public void begin() {
		jdbcConnectionManager.getConnectionForTransaction();
	}

	public void commit() {
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
		final Connection con = jdbcConnectionManager.getConnection();
		if (con != null) {
			try {
				con.rollback();
				con.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
			jdbcConnectionManager.releaseConnectionForTransaction();
		}
	}

}
