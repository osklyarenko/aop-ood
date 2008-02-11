package net.chrisrichardson.bankingExample.domain;

import java.util.Calendar;
import java.util.Date;

public class AccountMother {

	private static int counter;
	
	public static Account makeNoOverdraftAllowedAccount(double initialBalance) {
		return new Account(generateId(), initialBalance, Account.NEVER, new Date(), 0.0, 0);
	}

  private static String generateId() {
    return "A." + System.currentTimeMillis() + counter++;
  }

  static Account makeOverdraftAllowedAccount(int yearsOpen, int requiredYearsOpen, int overdraftLimit) {
    return new Account(generateId(), MoneyTransferServiceTests.FROM_ACCOUNT_INITIAL_BALANCE, Account.ALLOWED, yearsAgo(yearsOpen), requiredYearsOpen, -overdraftLimit);
  }

  static Date yearsAgo(int years) {
    Calendar c = Calendar.getInstance();
    c.add(Calendar.YEAR, -years);
    return c.getTime();
  }

}
