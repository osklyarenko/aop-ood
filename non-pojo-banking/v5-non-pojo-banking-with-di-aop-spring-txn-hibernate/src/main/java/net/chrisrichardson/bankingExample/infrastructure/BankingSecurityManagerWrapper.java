package net.chrisrichardson.bankingExample.infrastructure;


public class BankingSecurityManagerWrapper {

 
  public void verifyCallerAuthorized(Class<?> targetType, String methodName) {
    BankingSecurityManager.verifyCallerAuthorized(targetType, methodName);
  }

}
