package net.chrisrichardson.bankingExample.domain;

import java.util.Calendar;

public class AccountMother {

	private static int counter;
	
	public static Account makeAccountWithNoOverdraft(double initialBalance) {
		return new Account(generateAccountId(), initialBalance, new NoOverdraftAllowed());
	}

  static String generateAccountId() {
    return "A." + System.currentTimeMillis() + counter++;
  }

  public static Account makeAccount(double initialBalance, OverdraftPolicy overdraftPolicy) {
    return new Account(generateAccountId(), initialBalance, overdraftPolicy);
  }

  public static Account makeAccount(double initialBalance, OverdraftPolicy overdraftPolicy, double ageInYears) {
    Calendar c = Calendar.getInstance();
    c.add(Calendar.MONTH, (int)Math.round(-ageInYears * 12));
    return new Account(generateAccountId(), initialBalance, overdraftPolicy, c.getTime());
  }

}
