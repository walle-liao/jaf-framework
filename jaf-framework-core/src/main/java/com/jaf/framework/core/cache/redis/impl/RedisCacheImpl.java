package com.jaf.framework.core.cache.redis.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;

import com.jaf.framework.core.cache.key.EntityCacheKeyGenerator;
import com.jaf.framework.core.cache.redis.RedisCache;
import com.jaf.framework.core.model.BaseEntity;
import com.jaf.framework.util.Assert;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2015年11月15日
 * @since 1.0
 */
public class RedisCacheImpl implements RedisCache {
	
	private RedisTemplate<String, Object> redisTemplate;
	
	private final EntityCacheKeyGenerator comoonEntityKeyGenerator = new EntityCacheKeyGenerator();
	
	@Override
	public boolean set(String key, int exp, Object value) {
		redisTemplate.opsForValue().set(key, value, exp, TimeUnit.SECONDS);
		return true;
	}
	
	
	@Override
	public boolean set(String key, Object value) {
		redisTemplate.opsForValue().set(key, value);
		return true;
	}


	@Override
	public boolean add(String key, int exp, Object value) {
		if(redisTemplate.hasKey(key))
			return false;
		
		redisTemplate.opsForValue().set(key, value, exp, TimeUnit.SECONDS);
		return true;
	}
	
	
	@Override
	public boolean replace(String key, int exp, Object value) {
		if(!redisTemplate.hasKey(key))
			return false;
		
		redisTemplate.opsForValue().set(key, value, exp, TimeUnit.SECONDS);
		return true;
	}
	
	
	@Override
	public boolean replace(String key, Object value) {
		if(!redisTemplate.hasKey(key))
			return false;
		
		redisTemplate.opsForValue().set(key, value);
		return true;
	}
	

	@Override
	public boolean update(String key, Object value) {
		redisTemplate.opsForValue().set(key, value);
		return true;
	}


	@Override
	public boolean delete(String key) {
		redisTemplate.delete(key);
		return true;
	}
	
	
	@Override
	public Object get(String key) {
		Assert.hasText(key);
		return redisTemplate.opsForValue().get(key);
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(String key, Class<T> clazz) {
		Assert.notNull(clazz);
		return (T) get(key);
	}

	
	@Override
	public boolean setEntity(BaseEntity<?> entity, int exp) {
		String key = comoonEntityKeyGenerator.generateKey(entity);
		return set(key, exp, entity);
	}


	@Override
	public <T extends BaseEntity<?>> T getEntity(String id, Class<T> clazz) {
		String key = comoonEntityKeyGenerator.getKeyByClass(clazz, id);
		return get(key, clazz);
	}
	
	
	@Override
	public BaseEntity<?> updateEntity(BaseEntity<?> newEntity) {
		String key = comoonEntityKeyGenerator.generateKey(newEntity);
		if(redisTemplate.hasKey(key)) {
			BaseEntity<?> oldEntity = (BaseEntity<?>) redisTemplate.opsForValue().getAndSet(key, newEntity);
			return oldEntity;
		}
		return null;
	}


	@Override
	public <T extends BaseEntity<?>> boolean deleteEntity(String id, Class<T> clazz) {
		String key = comoonEntityKeyGenerator.getKeyByClass(clazz, id);
		return delete(key);
	}
	
	
	@Override
	public boolean deleteEntity(BaseEntity<?> entity) {
		String key = comoonEntityKeyGenerator.generateKey(entity);
		return delete(key);
	}


	public RedisTemplate<String, Object> getRedisTemplate() {
		return redisTemplate;
	}
	
	public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	
}
