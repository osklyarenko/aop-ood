package net.chrisrichardson.bankingExample.infrastructure;

import org.springframework.stereotype.Component;


@Component
public class AuditingManager {

	public void audit(Class<?> targetType, String methodName, Object[] args) {
		// TODO Do something
	}

}
