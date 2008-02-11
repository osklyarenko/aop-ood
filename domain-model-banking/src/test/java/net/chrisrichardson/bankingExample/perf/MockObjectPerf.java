package net.chrisrichardson.bankingExample.perf;


import net.chrisrichardson.bankingExample.domain.MoneyTransferServiceTests;

public class MockObjectPerf {
  
  public static void main(String[] args) throws Exception {
    TestUtil.runLotsOfTests(MoneyTransferServiceTests.class, "testTransfer_normal", 1000);
  }

}
