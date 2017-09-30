package com.jaf.framework.distribution.lock.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @since 1.0
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DistributionLock {
	
	/**
	 * 分布式锁名称
	 * @return
	 */
	String name();
	
	/**
	 * 锁最长持有时间(单位：秒)，不设置时默认30s，最大不超过30分钟
	 * 需要根据应用不同选择合适的值
	 * @return
	 */
    int leaseTime() default -1;

    /**
     * 与 tryLockOnly 配合使用，设置 tryOnly 的超时时间
     * @return
     */
    int waitTime() default -1;
    
    /**
     * 是否使用 tryLock 方式获取锁
     * @return
     */
    boolean tryLockOnly() default true;
    
}
