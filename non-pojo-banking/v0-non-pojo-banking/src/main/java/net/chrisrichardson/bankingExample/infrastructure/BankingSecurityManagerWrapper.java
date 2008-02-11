package net.chrisrichardson.bankingExample.infrastructure;


public class BankingSecurityManagerWrapper {

  private static BankingSecurityManagerWrapper theInstance  = new BankingSecurityManagerWrapper();
  
  public static BankingSecurityManagerWrapper getInstance() {
    return theInstance;
  }

  public void verifyCallerAuthorized(Class<?> targetType, String methodName) {
    BankingSecurityManager.verifyCallerAuthorized(targetType, methodName);
  }

}
