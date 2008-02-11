package net.chrisrichardson.bankingExample.infrastructure.aspects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.dao.ConcurrencyFailureException;

@Aspect
public class TransactionRetryAspect extends AbstractOrderedAspect {

  private Log logger = LogFactory.getLog(getClass());
  private static final int MAX_RETRIES = 2;

  
  @Pointcut("execution(public * net.chrisrichardson..*ServiceProceduralImpl.*(..))")
  private void serviceCall() {
  }

  @Around("serviceCall()")
  public Object retryTransaction(ProceedingJoinPoint jp) throws Throwable {
    int retryCount = 0;
    logger.debug("entering transaction retry");
    while (true) {
      try {
        Object result = jp.proceed();
        logger.debug("leaving transaction retry");
        return result;
      } catch (ConcurrencyFailureException e) {
        if (retryCount++ > MAX_RETRIES)
          throw e;
        logger.debug("retrying transaction");
      }
    }
  }

}
