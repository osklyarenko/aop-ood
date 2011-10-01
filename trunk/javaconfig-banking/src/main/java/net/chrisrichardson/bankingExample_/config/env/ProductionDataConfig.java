package net.chrisrichardson.bankingExample_.config.env;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import net.chrisrichardson.bankingExample_.config.DataConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("production")
public class ProductionDataConfig implements DataConfig {

  @Bean
  public DataSource dataSource() throws Exception {
    Context ctx = new InitialContext();
    return (DataSource) ctx.lookup("java:comp/env/jdbc/datasource");
  }

}
