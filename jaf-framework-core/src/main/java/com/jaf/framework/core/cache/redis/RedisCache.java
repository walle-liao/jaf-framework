package com.jaf.framework.core.cache.redis;

import com.jaf.framework.core.cache.Cache;
import com.jaf.framework.core.model.BaseEntity;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2015年11月15日
 * @since 1.0
 */
public interface RedisCache extends Cache {
	
	boolean set(String key, Object value);
	
	
	public boolean replace(String key, Object value);

	
	/**
	 * 不同于 replace，如果没有key则新增，有则替换
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean update(String key, Object value);
	
	
	<T> T get(String key, Class<T> clazz);
	
	
	/**
	 * 缓存实体对象到缓存中，没有设置对应的key，将使用默认的实体类对应的 key 生成器来生成缓存 key
	 * @param entity 实体
	 * @param exp 超时时间
	 * @return
	 */
	boolean setEntity(BaseEntity<?> entity, int exp);
	
	
	/**
	 * 获取缓存中的实体对象，对应的 Entity 必须是由 {@link #setEntity(BaseEntity)} 方法设置进缓存的才可用
	 * @param id 实体类ID
	 * @param clazz 实体类类型
	 * @return
	 */
	<T extends BaseEntity<?>> T getEntity(String id, Class<T> clazz);
	
	
	/**
	 * 更新缓存中的实体对象，返回更新前的实体对象
	 * 如果缓存中之前没有该实体对象，则不会更新，并且返回 null
	 * 
	 * @param newEntity 新的实体对象
	 * @return 更新前缓存中的实体对象
	 */
	BaseEntity<?> updateEntity(BaseEntity<?> newEntity);
	
	
	<T extends BaseEntity<?>> boolean deleteEntity(String id, Class<T> clazz);
	
	
	boolean deleteEntity(BaseEntity<?> entity);
	
}
