package net.chrisrichardson.bankingExample.perf;



public class DBPerf2 {
  
  public static void main(String[] args) {
    int n = 400;
    MyTst test = new MyTst(n);
    test.setName("testTransfer");
    TestUtil.timeTest(test, n);
    
  }

}
