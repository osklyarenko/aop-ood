package net.chrisrichardson.bankingExample.domain;

public class OverdraftPolicy {
  public static final int ALLOWED = 2;
  public static final int NEVER = 1;

  private int id;
  private int overdraftPolicyType;
  private double requiredYearsOpen;
  private double limit;

  OverdraftPolicy() {
  }
  
  public OverdraftPolicy(int overdraftPolicyType, double requiredYearsOpen,
      double limit) {
    this.overdraftPolicyType = overdraftPolicyType;
    this.requiredYearsOpen = requiredYearsOpen;
    this.limit = limit;
  }

  public int getOverdraftPolicyType() {
    return overdraftPolicyType;
  }

  public void setOverdraftPolicyType(int overdraftPolicy) {
    this.overdraftPolicyType = overdraftPolicy;
  }

  public double getRequiredYearsOpen() {
    return requiredYearsOpen;
  }

  public void setRequiredYearsOpen(double requiredYearsOpen) {
    this.requiredYearsOpen = requiredYearsOpen;
  }

  public double getLimit() {
    return limit;
  }

  public void setLimit(double limit) {
    this.limit = limit;
  }

}