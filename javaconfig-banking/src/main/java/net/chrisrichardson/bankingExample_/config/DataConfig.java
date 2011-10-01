package net.chrisrichardson.bankingExample_.config;

import javax.sql.DataSource;

public interface DataConfig {
  DataSource dataSource() throws Exception;
}
