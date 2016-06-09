package com.jaf.framework.core.cache;

import javax.annotation.Resource;

import org.junit.Test;

import com.jaf.framework.core.IntegrationTests;
import com.jaf.framework.core.cache.memcached.XMemcachedCache;


/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2015年6月28日
 * @since 1.0
 */
public class XMemcachedCacheTests extends IntegrationTests {
	
	@Resource(name = "xMemcachedCache")
	private XMemcachedCache xMemcachedCache;
	
	
	@Test
	public void setTest() {
		xMemcachedCache.set("t1", Integer.MAX_VALUE, "test1");
	}
	
	
	@Test
	public void getTest() {
		String value = (String) xMemcachedCache.get("t1");
		System.out.println(value);
	}
	
	
	@Test
	public void deleteTest() {
		xMemcachedCache.delete("t1");
		
		String value = (String) xMemcachedCache.get("t1");
		System.out.println(value);
	}
	
}
