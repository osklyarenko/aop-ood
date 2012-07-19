package net.chrisrichardson.banking.web.dtos;

import org.apache.commons.lang.builder.ToStringBuilder;

public class AccountInfo {
  private String accountId;
  private double balance;
  private int id;

  public AccountInfo() {
  }

  public AccountInfo(int id, String accountId, double balance) {
    this.id = id;
    this.accountId = accountId;
    this.balance = balance;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getAccountId() {
    return accountId;
  }

  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }

  public double getBalance() {
    return balance;
  }

  public void setBalance(double balance) {
    this.balance = balance;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
