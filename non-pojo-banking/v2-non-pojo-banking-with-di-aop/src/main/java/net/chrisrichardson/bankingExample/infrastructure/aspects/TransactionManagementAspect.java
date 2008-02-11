package net.chrisrichardson.bankingExample.infrastructure.aspects;

import net.chrisrichardson.bankingExample.domain.MoneyTransferException;
import net.chrisrichardson.bankingExample.infrastructure.TransactionManager;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class TransactionManagementAspect extends AbstractOrderedAspect {

  private TransactionManager transactionManager;

  
  // Note: we are changing the ordering
  
  public TransactionManagementAspect(TransactionManager transactionManager) {
    this.transactionManager = transactionManager;
  }

  @Pointcut("execution(public * net.chrisrichardson..*ServiceProceduralImpl.*(..))")
  private void serviceCall() {
  }

  @Around("serviceCall()")
  public Object manageTransaction(ProceedingJoinPoint jp) throws Throwable {
    transactionManager.begin();

    try {
      Object result = jp.proceed();
      transactionManager.commit();
      return result;
    } catch (MoneyTransferException e) {
      transactionManager.commit();
      throw e;
    } finally {
      transactionManager.rollbackIfNecessary();
    }
  }

 

}
