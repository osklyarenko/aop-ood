package net.chrisrichardson.bankingExample.client;

import net.chrisrichardson.bankingExample.domain.Account;
import net.chrisrichardson.bankingExample.domain.AccountService;
import net.chrisrichardson.bankingExample.domain.BankingTransaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.stereotype.Component;

@Component
@Qualifier("clientDelegate")
public class AccountServiceDelegate implements AccountService {

  private static final int MAX_RETRIES = 2;

  private AccountService service;

  @Autowired
  public AccountServiceDelegate(AccountService accountService) {
    this.service = accountService;
  }

  public BankingTransaction transfer(String fromAccountId, String toAccountId,
      double amount) {
    int retryCount = 0;
    while (true) {
      try {
        return service.transfer(fromAccountId, toAccountId, amount);
      } catch (ConcurrencyFailureException e) {
        if (retryCount++ > MAX_RETRIES)
          throw e;
      }
    }

  }

  public void create(Account account) {
    int retryCount = 0;
    while (true) {
      try {
        service.create(account);
      } catch (ConcurrencyFailureException e) {
        if (retryCount++ > MAX_RETRIES)
          throw e;
      }
    }
  }
}
