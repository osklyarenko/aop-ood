package net.chrisrichardson.bankingExample.perf;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

public class TestUtil {

  static void runLotsOfTests(Class testType, String testName, int numTests) throws Exception {
    Test test = TestUtil.makeLotsOfTests(numTests, testType, testName);
    timeTest(test, numTests);
  }

  static void timeTest(Test test, int n) {
    TestRunner runner = new TestRunner(/* new PrintStream(new ByteArrayOutputStream())*/);
    long startTime = System.currentTimeMillis();
    runner.doRun(test);
    long endTime = System.currentTimeMillis();
    long duration = endTime - startTime;
    double testsPerSecond = n / (duration / 1000.0);
    System.out.println("Rate: " + testsPerSecond);
  }

  static Test makeLotsOfTests(int n, Class testsType, String testName) throws Exception {
    TestSuite ts = new TestSuite();
    TestCase moneyTransferServiceTests = (TestCase) testsType.newInstance();
    moneyTransferServiceTests.setName(testName);
    for (int i = 0; i < n; i++) {
      ts.addTest(moneyTransferServiceTests);
    }
    return ts;
  }

}
