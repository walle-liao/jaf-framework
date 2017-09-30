package com.jaf.framework.core.common;

import org.springframework.beans.factory.InitializingBean;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Supplier;

/**
 * 系统通用线程池
 * 
 * @author liaozhicheng.cn@163.com
 * @since 1.0
 */
public class SystemExecutorServiceHolder implements InitializingBean {

	private ExecutorService executor;
	private int poolSize;

	public <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier) {
		return CompletableFuture.supplyAsync(supplier, executor);
	}

	/**
	 * 用在定时器之类的项目里面，没有登陆人员的后台程序
	 * @param runnable
	 */
	public void execute(Runnable runnable) {
		executor.execute(runnable);
	}
	
	public <T> Future<T> submit(final Callable<T> callable) {
		return this.submit(callable);
	}

	/**
	 * 会等待所有的 task 都完成之后才返回
	 * @param tasks
	 * @return
	 * @throws InterruptedException
	 */
	public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
		return executor.invokeAll(tasks);
	}
	
	public ExecutorService getExecutorService() {
		return this.executor;
	}
	
	public int getPoolSize() {
		return this.poolSize;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		poolSize = Runtime.getRuntime().availableProcessors();
		executor = Executors.newFixedThreadPool(poolSize);
	}

}
