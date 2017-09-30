package com.jaf.framework.distribution.lock;

import java.util.concurrent.TimeUnit;

import com.jaf.framework.distribution.DistributionManager;
import com.jaf.framework.distribution.lock.annotation.DistributionLock;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.core.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @since 1.0
 */
@Aspect
@Component
public class DistributionLockAspect {
	
	// 默认的锁持有时间（默认30s）
	private static final int DEFAULT_LEASE_TIME = 30;
	
	// 锁最长持有时间（30分钟）
	private static final int MAX_LEASE_TIME = 30 * 60;
	
	@Autowired
	private DistributionManager manager;
	
	@Around(value = "@annotation(distributionLock)")
	public Object aroundMethod(ProceedingJoinPoint proceedingJoinPoint,
			DistributionLock distributionLock) throws Throwable {
		Object result = null;
		
		String name = distributionLock.name();
		// 锁最大持有时间，如果未设值时，默认5s，不操过2分钟
		int leaseTime = distributionLock.leaseTime();
		leaseTime = leaseTime < 0 ? DEFAULT_LEASE_TIME : (leaseTime > MAX_LEASE_TIME ? MAX_LEASE_TIME : leaseTime);
		
		int waitTime = distributionLock.waitTime();
		boolean tryLockOnly = distributionLock.tryLockOnly();
		
		RLock lock = manager.getLock(name);
		try {
			if(tryLockOnly) {
				boolean lockFlag = lock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
				if(!lockFlag) {
					throw new GetDistributionLockTimeOutException(name, "acquired lock failed.");
				}
			} else {
				lock.lock(leaseTime, TimeUnit.SECONDS);
			}
			
			result = proceedingJoinPoint.proceed();
		} catch (Throwable e) {
			throw e;
		} finally {
			if(lock.isHeldByCurrentThread()) {
				lock.unlock();
			}
		}
		return result;
	}
	
}
