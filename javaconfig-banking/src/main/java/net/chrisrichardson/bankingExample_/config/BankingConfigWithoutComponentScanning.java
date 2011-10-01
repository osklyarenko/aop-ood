package net.chrisrichardson.bankingExample_.config;

import java.util.Properties;

import net.chrisrichardson.bankingExample.domain.AccountRepository;
import net.chrisrichardson.bankingExample.domain.BankingTransactionRepository;
import net.chrisrichardson.bankingExample.domain.MoneyTransferService;
import net.chrisrichardson.bankingExample.domain.MoneyTransferServiceImpl;
import net.chrisrichardson.bankingExample.domain.hibernate.HibernateAccountRepository;
import net.chrisrichardson.bankingExample.domain.hibernate.HibernateBankingTransactionRepository;

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
@ComponentScan({"net.chrisrichardson.bankingExample_.config.env"})
@ImportResource({ "classpath:/appCtx/transactions.xml", "classpath:/appCtx/load-properties.xml" })
public class BankingConfigWithoutComponentScanning {

  @Autowired
  private DataConfig dataConfig;
  
  @Value("${hibernate.dialect}")
  private String dialect;

  @Bean
  public MoneyTransferService moneyTransferServiceImpl(AccountRepository accountRepository, BankingTransactionRepository bankingTransactionRepository) {
    return new MoneyTransferServiceImpl(accountRepository, bankingTransactionRepository);
  }

  @Bean
  public AccountRepository accountRepository() throws Exception {
    return new HibernateAccountRepository(sessionFactory());
  }

  @Bean
  public BankingTransactionRepository bankingTransactionRepository() throws Exception {
    return new HibernateBankingTransactionRepository(sessionFactory());
  }

  @Bean
  public SessionFactory sessionFactory() throws Exception  {
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
