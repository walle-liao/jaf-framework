package com.jaf.framework.distribution.lock;


import com.jaf.framework.distribution.DistributionManager;
import com.jaf.framework.distribution.lock.annotation.DistributionLock;
import org.redisson.core.RLock;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @since 1.0
 */
@Component("myService")
public class MyService {
	
	@Resource
	private DistributionManager distributionManager;
	
	private void doService() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("thread exited. id : " + Thread.currentThread().getId());
	}

	// 默认方式获取锁，立即返回，如果锁空闲，则获取成，如果锁已经被占用，则获取锁失败抛出 GetDistributionLockTimeOutException，需要在上层调用方法中处理该异常
	// 锁最大持有时间10s，不管10s业务方法是否返回，锁都会释放
	@DistributionLock(name = "MyService#doService1", leaseTime = 10)
	public void doService1() {
		doService();
	}
	
	// tryLockOnly = false, 不使用 trylock 方式获取锁，该方法不会立即返回，会一直阻塞等待锁获取成功
	@DistributionLock(name = "MyService#doService2", leaseTime = 10, tryLockOnly = false)
	public void doService2() {
		doService();
	}
	
	// waitTime = 5，如果锁空闲则可以立刻获取锁，并返回，如果锁被占用，阻塞等待5s时，如果5s内可以获取到锁则正常返回
	// 如果5s后还不能成功获取锁，则抛出 GetDistributionLockTimeOutException，需要在上层调用方法中处理该异常
	@DistributionLock(name = "MyService#doService3", waitTime = 5, leaseTime = 10)
	public void doService3() {
		doService();
	}
	
	public void doService4() {
		RLock lock = distributionManager.getLock("MyService#doService4");
		
		try {
			// 通过手动方式获取锁
			lock.lock(10, TimeUnit.SECONDS);
			
			doService();
		} finally {
			if(lock.isHeldByCurrentThread()) {
				lock.unlock();
			}
		}
	}
	
}
