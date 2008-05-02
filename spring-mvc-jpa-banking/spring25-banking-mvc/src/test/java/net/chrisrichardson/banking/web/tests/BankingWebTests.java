package net.chrisrichardson.banking.web.tests;

import net.chrisrichardson.umangite.LaunchOncePerSuiteWebTest;

import org.testng.annotations.Test;

public class BankingWebTests extends LaunchOncePerSuiteWebTest {

  @Override
  protected String[] getConfigLocations() {
    return null;
  }

  @Override
  protected String[] getUmangiteConfigLocations() {
    return new String[] {"/appCtx.umangite/*.xml"};
  }
  
  @Test
  public void testTransfer() {
    open("/webapp");
    assertBalance("xyz", "50.0");
    assertBalance("abc", "100.0");
    select("fromAccount", "xyz");
    select("toAccount", "abc");
    type("amount", "20");
    clickAndWait("submit");
    assertBalance("xyz", "30.0");
    assertBalance("abc", "120.0");
  }

  private void assertBalance(String accountId, String expectedBalance) {
    assertElementPresent(String.format("//table[@id='account']//td[text()='%s']/parent::*//td[text()='%s']", accountId, expectedBalance));
  }

}
