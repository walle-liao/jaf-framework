package com.jaf.framework.core.cache.memcached.impl;

import net.spy.memcached.MemcachedClient;

import org.apache.log4j.Logger;

import com.jaf.framework.core.cache.memcached.XMemcachedCache;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2015年6月28日
 * @since 1.0
 */
public class XMemcachedCacheImpl implements XMemcachedCache {
	
	private static final Logger LOG = Logger.getLogger(XMemcachedCacheImpl.class);
	
	private MemcachedClient memcachedClient;
	
	
	@Override
	public boolean set(String key, int exp, Object value) {
		try {
			return memcachedClient.set(key, exp, value).get();
		}
		catch (Exception e) {
			LOG.error("Exception", e);
		}
		return false;
	}
	
	
	@Override
	public boolean add(String key, int exp, Object value) {
		try {
			return memcachedClient.add(key, exp, value).get();
		}
		catch (Exception e) {
			LOG.error("Exception", e);
		}
		return false;
	}
	
	
	@Override
	public boolean replace(String key, int exp, Object value) {
		try {
			return memcachedClient.replace(key, exp, value).get();
		}
		catch (Exception e) {
			LOG.error("Exception", e);
		}
		return false;
	}
	
	
	@Override
	public boolean delete(String key) {
		try {
			return memcachedClient.delete(key).get();
		}
		catch (Exception e) {
			LOG.error("Exception", e);
		}
		return false;
	}
	
	
	@Override
	public Object get(String key) {
		return memcachedClient.get(key);
	}

	
	public MemcachedClient getMemcachedClient() {
		return memcachedClient;
	}

	public void setMemcachedClient(MemcachedClient memcachedClient) {
		this.memcachedClient = memcachedClient;
	}
	
}
