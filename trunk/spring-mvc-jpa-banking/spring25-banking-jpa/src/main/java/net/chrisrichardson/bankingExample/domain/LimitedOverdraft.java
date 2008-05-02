package net.chrisrichardson.bankingExample.domain;

import javax.persistence.Entity;

@Entity
public class LimitedOverdraft extends OverdraftPolicyImpl {

	private double limit;
	private double requiredYearsOpen;

	LimitedOverdraft() {
	}

	public LimitedOverdraft(double limit, double requiredYearsOpen) {
		this.limit = limit;
		this.requiredYearsOpen = requiredYearsOpen;
	}

	public void beforeDebitCheck(Account account, double originalBalance,
			double newBalance) throws MoneyTransferException {
// Originally I wrote this which is so wrong
//		if (account.getYearsOpen() < requiredYearsOpen || newBalance < limit)
//			throw new MoneyTransferException("Limit exceeded");
		if (newBalance < 0 && (account.getYearsOpen() < requiredYearsOpen || newBalance < limit))
		  throw new MoneyTransferException("Limit exceeded");

	}

	public void afterDebitAction(Account account, double originalBalance,
			double newBalance) {
	}

}
