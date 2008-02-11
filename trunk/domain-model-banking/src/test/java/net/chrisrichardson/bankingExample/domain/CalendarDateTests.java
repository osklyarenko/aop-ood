package net.chrisrichardson.bankingExample.domain;

import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;

public class CalendarDateTests extends TestCase {

  public void testGetYearsOpenNow() {
    CalendarDate c = new CalendarDate(new Date());
    assertEquals(0.0, c.getYearsOpen());
  }

  public void testGetYearsOpen1Month() {
    Date date = makeDateMonthsInPast(1);
    CalendarDate c = new CalendarDate(date);
    assertEquals(1.0/12.0, c.getYearsOpen());
  }

  public void testGetYearsOpen11Month() {
    Date date = makeDateMonthsInPast(11);
    CalendarDate c = new CalendarDate(date);
    assertEquals(11.0/12.0, c.getYearsOpen());
  }

  private Date makeDateMonthsInPast(int numberOfMonths) {
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.MONTH, -numberOfMonths);
    Date date = cal.getTime();
    return date;
  }

}
