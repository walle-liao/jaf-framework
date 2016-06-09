package com.jaf.framework.core.cache.key;


/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2015年11月15日
 * @since 1.0
 */
public interface CacheKeyGenerator<T> {
	
	String generateKey(T obj);

}
