package net.chrisrichardson.bankingExample.domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class CalendarDate implements Serializable {

	private Date date;

	CalendarDate() {
	}
	
	public CalendarDate(Date date) {
		this.date = date;
	}

	public Date getDate() {
		return date;
	}

	public double getYearsOpen() {
		Calendar then = Calendar.getInstance();
		then.setTime(date);
		Calendar now = Calendar.getInstance();
		
		int yearsOpened = now.get(Calendar.YEAR) - then.get(Calendar.YEAR);
		int monthsOpened = now.get(Calendar.MONTH) - then.get(Calendar.MONTH);
		if (monthsOpened < 0) {
			yearsOpened--;
			monthsOpened += 12;
		}
		return yearsOpened + (monthsOpened/12.0);
		
	}

}
