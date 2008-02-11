package net.chrisrichardson.bankingExample.infrastructure.aspects;

import net.chrisrichardson.bankingExample.infrastructure.AuditingManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class AuditingAspect extends AbstractOrderedAspect {

  Log logger = LogFactory.getLog(getClass());

  private AuditingManager auditingManager;

  public AuditingAspect(AuditingManager auditingManager) {
    super();
    this.auditingManager = auditingManager;
  }

  @Pointcut("execution(public * net.chrisrichardson..*ServiceProceduralImpl.*(..))")
  private void serviceCall() {
  }

  @Before("serviceCall()")
  public void doSecurityCheck(JoinPoint jp) throws Throwable {

    auditingManager.audit(jp.getTarget().getClass(), jp.getSignature()
        .getName(), jp.getArgs());
  }

}
