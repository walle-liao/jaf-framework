package com.jaf.framework.core.cache.ehcache.impl;

import org.ehcache.Cache;

import com.jaf.framework.core.cache.ehcache.Ehcache;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年6月27日
 * @since 1.0
 */
public class EhcacheImpl implements Ehcache {
	
	private Cache<String, Object> cache;

	@Override
	public boolean set(String key, int exp, Object value) {
		cache.put(key, value);
		return true;
	}

	@Override
	public boolean add(String key, int exp, Object value) {
		return false;
	}

	@Override
	public boolean replace(String key, int exp, Object value) {
		return false;
	}

	@Override
	public boolean delete(String key) {
		return false;
	}

	@Override
	public Object get(String key) {
		return null;
	}

	public void setCache(Cache cache) {
		this.cache = cache;
	}

}
