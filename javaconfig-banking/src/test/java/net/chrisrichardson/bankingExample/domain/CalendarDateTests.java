package net.chrisrichardson.bankingExample.domain;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

public class CalendarDateTests {

  @Test
  public void testGetYearsOpenNow() {
    CalendarDate c = new CalendarDate(new Date());
    assertEquals(0.0, c.getYearsOpen(), 0.0);
  }

  @Test
  public void testGetYearsOpen1Month() {
    Date date = makeDateMonthsInPast(1);
    CalendarDate c = new CalendarDate(date);
    assertEquals(1.0/12.0, c.getYearsOpen(), 0.0);
  }

  @Test
  public void testGetYearsOpen11Month() {
    Date date = makeDateMonthsInPast(11);
    CalendarDate c = new CalendarDate(date);
    assertEquals(11.0/12.0, c.getYearsOpen(), 0.0);
  }

  private Date makeDateMonthsInPast(int numberOfMonths) {
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.MONTH, -numberOfMonths);
    Date date = cal.getTime();
    return date;
  }

}
