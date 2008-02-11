/**
 * 
 */
package net.chrisrichardson.bankingExample.perf;

import net.chrisrichardson.bankingExample.spring.SpringMoneyTransferServiceTests;

public class MyTst extends SpringMoneyTransferServiceTests {
  
  private int n = 1;

  public MyTst() {
  }
  
  public MyTst(int n) {
    this.n = n;
  }

  @Override
  public void testTransfer() {
  for (int i = 0; i < n; i++)
    super.testTransfer();
  }
}