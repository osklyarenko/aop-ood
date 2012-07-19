package net.chrisrichardson.banking.web.controllers;

import javax.annotation.PostConstruct;

import net.chrisrichardson.bankingExample.domain.MoneyTransferService;
import net.chrisrichardson.bankingExample.domain.NoOverdraftAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountCreator {

  
  private final MoneyTransferService moneyTransferService;

  @Autowired
  public AccountCreator(MoneyTransferService moneyTransferService) {
    this.moneyTransferService = moneyTransferService;
  }

  @PostConstruct
  public void createAccounts() {
    moneyTransferService.addAccount("xyz", 50, new NoOverdraftAllowed());
    moneyTransferService.addAccount("abc", 100, new NoOverdraftAllowed());
  }
}
