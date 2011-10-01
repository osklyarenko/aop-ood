package net.chrisrichardson.bankingExample_.config;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.orm.hibernate3.SessionFactoryBuilder;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@ComponentScan({"net.chrisrichardson.bankingExample", "net.chrisrichardson.bankingExample_.config.env"})
@ImportResource({ "classpath:/appCtx/transactions.xml", "classpath:/appCtx/load-properties.xml" })
public class BankingConfig {

  @Autowired
  private DataConfig dataConfig;
  
  @Value("${hibernate.dialect}")
  private String dialect;

  @Bean
  public SessionFactory sessionFactory() throws Exception {
    Properties hibernateProperties = new Properties();
    hibernateProperties.setProperty("hibernate.show_sql", "true");
    hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");
    hibernateProperties.setProperty("hibernate.dialect", dialect);
    return new SessionFactoryBuilder(dataConfig.dataSource())
        .setMappingResources("/net/chrisrichardson/bankingExample/domain/HibernateBankingExample.hbm.xml")
        .setHibernateProperties(hibernateProperties).buildSessionFactory();
  }

  @Bean
  public PlatformTransactionManager transactionManager(SessionFactory sessionFactory) {
    return new HibernateTransactionManager(sessionFactory);
  }
}
