package net.chrisrichardson.banking.web.controllers;

import javax.servlet.http.HttpServletResponse;

import net.chrisrichardson.banking.web.dtos.AccountInfo;
import net.chrisrichardson.bankingExample.domain.Account;
import net.chrisrichardson.bankingExample.domain.MoneyTransferService;
import net.chrisrichardson.bankingExample.domain.NoOverdraftAllowed;
import net.chrisrichardson.bankingExample.domain.OverdraftPolicy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
public class AccountController {

  @Autowired
  private MoneyTransferService moneyTransferService;
  
  @RequestMapping(value = "/accounts/{accountId}", method = RequestMethod.GET)
  @ResponseBody
  public AccountInfo getAccount(@PathVariable String accountId) {
    Account account = moneyTransferService.findAccountByid(accountId);
    return makeAccountInfo(account);
  }

  private AccountInfo makeAccountInfo(Account account) {
    return new AccountInfo(account.getId(), account.getAccountId(), account.getBalance());
  }

  @RequestMapping(value = "/accounts", method = RequestMethod.POST)
  @ResponseStatus( HttpStatus.CREATED )
  public void createAccount(@RequestBody AccountInfo accountInfo, UriComponentsBuilder builder, HttpServletResponse response) {
    OverdraftPolicy overdraftPolicy = new NoOverdraftAllowed();
    Account account = moneyTransferService.addAccount(accountInfo.getAccountId(), accountInfo.getBalance(), overdraftPolicy);
    String uriString = builder.path("/accounts/{accountId}").buildAndExpand(account.getAccountId()).toUriString();
    response.setHeader("location", uriString);
  }
}
