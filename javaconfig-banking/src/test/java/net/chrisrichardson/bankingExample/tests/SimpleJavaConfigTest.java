package net.chrisrichardson.bankingExample.tests;

import net.chrisrichardson.bankingExample.domain.AccountRepository;
import net.chrisrichardson.bankingExample_.config.BankingConfig;

import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SimpleJavaConfigTest {
  
  @Test
  public void test() {
    ConfigurableApplicationContext ctx = new AnnotationConfigApplicationContext(BankingConfig.class);
    AccountRepository repository = ctx.getBean(AccountRepository.class);
    System.out.println(repository);
    ctx.close();
  }

}
