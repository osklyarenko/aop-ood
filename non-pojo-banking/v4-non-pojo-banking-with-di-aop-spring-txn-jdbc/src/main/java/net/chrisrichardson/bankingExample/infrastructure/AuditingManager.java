package net.chrisrichardson.bankingExample.infrastructure;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

@Component
public class AuditingManager {

  private Log logger = LogFactory.getLog(getClass());
  
	public void audit(Class<?> targetType, String methodName, Object[] args) {
		logger.debug("Doing audit: " + targetType.getName() + "." + methodName);
	}

}
