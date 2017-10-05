package com.jaf.framework.distribution.lock;

import com.jaf.framework.distribution.BaseTest;
import com.jaf.framework.distribution.DistributionManager;
import org.junit.Assert;
import org.junit.Test;
import org.redisson.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.CountDownLatch;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @since 1.0
 */
public class DistributionLockTest extends BaseTest {
	
	@Autowired
	private MyService myService;
	@Autowired
	private DistributionManager distributionManager;
	
	@Test
	public void testUnlock() {
		myService.doService1();
		Assert.assertTrue(distributionManager.getLock("myServiceKey").tryLock());
	}
	
	@Test
	public void testConcurrency() throws InterruptedException {
		final CountDownLatch latch = new CountDownLatch(5);
		
		for(int i = 0; i < 5; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						myService.doService2();
					} finally {
						latch.countDown();
					}
				}
			}).start();
		}
		latch.await();
	}
	
	@Test(expected = GetDistributionLockTimeOutException.class)
	public void testConcurrencyMultiInstance() throws InterruptedException {
		final CountDownLatch latch = new CountDownLatch(10);
		
		for(int i = 0; i < 10; i++) {
			new Thread(new RedissonRunnable(createNewRedissonClient()) {
				@Override
				public void run() {
					try {
						myService.doService1();
					} finally {
						latch.countDown();
					}
				}
			}).start();
		}
		latch.await();
	}

	private static abstract class RedissonRunnable implements Runnable {
		
		protected final RedissonClient redissonClient;
		
		RedissonRunnable(RedissonClient redissonClient) {
			this.redissonClient = redissonClient;
		}

	}
	
}


