package net.chrisrichardson.bankingExample.infrastructure;


public class AuditingManager {

	private static AuditingManager theInstance = new AuditingManager();

	public static AuditingManager getInstance() {
		return theInstance;
	}

	public void audit(Class<?> targetType, String methodName, Object[] args) {
		// TODO Do something
	}

}
