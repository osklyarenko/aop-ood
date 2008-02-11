package net.chrisrichardson.bankingExample.infrastructure.aspects;

import net.chrisrichardson.bankingExample.infrastructure.BankingSecurityManagerWrapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class SecurityAspect extends AbstractOrderedAspect {

  Log logger = LogFactory.getLog(getClass());
  
  private BankingSecurityManagerWrapper bankingSecurityWrapper;

  
  public SecurityAspect(BankingSecurityManagerWrapper bankingSecurityWrapper) {
    this.bankingSecurityWrapper = bankingSecurityWrapper;
  }

  @Pointcut("execution(public * net.chrisrichardson..*ServiceProceduralImpl.*(..))")
  private void serviceCall() {
  }

  @Before("serviceCall()")
  public void doSecurityCheck(JoinPoint jp) throws Throwable {
    
    bankingSecurityWrapper.verifyCallerAuthorized(jp.getTarget().getClass(), jp.getSignature().getName());
  }

}
