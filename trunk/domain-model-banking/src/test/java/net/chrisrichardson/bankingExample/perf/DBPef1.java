package net.chrisrichardson.bankingExample.perf;



public class DBPef1 {
  
  public static void main(String[] args) throws Exception {
    TestUtil.runLotsOfTests(MyTst.class, "testTransfer", 2000);
  }

}
