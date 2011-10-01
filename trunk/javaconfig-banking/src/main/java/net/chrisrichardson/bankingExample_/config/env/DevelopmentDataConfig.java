package net.chrisrichardson.bankingExample_.config.env;

import javax.sql.DataSource;

import net.chrisrichardson.bankingExample_.config.DataConfig;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("default")
public class DevelopmentDataConfig implements DataConfig {

  @Value("${jdbc.driver.class}")
  private String driverClassName;

  @Value("${jdbc.url}")
  private String url;

  @Value("${jdbc.user}")
  private String username;

  @Value("${jdbc.password}")
  private String password;

  @Bean
  public DataSource dataSource() {
    BasicDataSource ds = new BasicDataSource();
    ds.setDriverClassName(driverClassName);
    ds.setUrl(url);
    ds.setUsername(username);
    ds.setPassword(password);
    return ds;
  }

}
