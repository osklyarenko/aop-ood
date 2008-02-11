package net.chrisrichardson.bankingExample.domain;

import java.util.Calendar;
import java.util.Date;

public class AccountMother {

	static final double FROM_ACCOUNT_INITIAL_BALANCE = 1000;
	static final double TO_ACCOUNT_INITIAL_BALANCE = 5000;

	private static int counter;

	public static Account makeNoOverdraftAllowedAccount(double initialBalance) {
		return new Account(generateId(), initialBalance, new Date(),
				new OverdraftPolicy(OverdraftPolicy.NEVER, 0.0, 0));
	}

	private static String generateId() {
		return "A." + System.currentTimeMillis() + counter++;
	}

	static Account makeOverdraftAllowedAccount(int yearsOpen,
			int requiredYearsOpen, int overdraftLimit) {
		return new Account(generateId(),
				AccountMother.FROM_ACCOUNT_INITIAL_BALANCE, yearsAgo(yearsOpen),
				new OverdraftPolicy(OverdraftPolicy.ALLOWED, requiredYearsOpen, -overdraftLimit));
	}

	static Date yearsAgo(int years) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.YEAR, -years);
		return c.getTime();
	}


}
