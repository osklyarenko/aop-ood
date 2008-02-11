package net.chrisrichardson.bankingExample.infrastructure.aspects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class LoggingAspect extends AbstractOrderedAspect {


  @Pointcut("execution(public * net.chrisrichardson..*ServiceProceduralImpl.*(..))")
  private void serviceCall() {
  }

  @Around("serviceCall()")
  public Object doLogging(ProceedingJoinPoint jp) throws Throwable {

    Log logger = LogFactory.getLog(jp.getTarget().getClass());
    Signature signature = jp.getSignature();
    String methodName = signature.getDeclaringTypeName() + "."
        + signature.getName();
    logger.debug("entering: " + methodName);
    try {
      Object result = jp.proceed();
      logger.debug("Leaving: " + methodName);
      return result;
    } catch (Exception e) {
      logger.debug("Exception thrown in " + methodName, e);
      throw e;
    }
  }



}
