package net.chrisrichardson.bankingExample.infrastructure;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class BankingSecurityManager {

  private static Log logger = LogFactory.getLog(BankingSecurityManager.class);
  
	public static void verifyCallerAuthorized(Class<?> targetType, String methodName) {
    logger.debug("Doing security check: " + targetType.getName() + "." + methodName);
	}


}
