package net.chrisrichardson.bankingExample.infrastructure;

import org.springframework.stereotype.Component;

@Component

public class BankingSecurityManagerWrapper {
 
  public void verifyCallerAuthorized(Class<?> targetType, String methodName) {
    BankingSecurityManager.verifyCallerAuthorized(targetType, methodName);
  }

}
