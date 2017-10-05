package com.jaf.framework.distribution.lock;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @since 1.0
 */
public class GetDistributionLockTimeOutException extends RuntimeException {

	private static final long serialVersionUID = -996552610733439642L;

	
	public GetDistributionLockTimeOutException() {
		super();
	}
	
	public GetDistributionLockTimeOutException(String lockName, String message) {
		super("[get lock: " + lockName + " time out] " + message);
	}
	
}
