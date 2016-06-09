package com.jaf.framework.core.cache.key;

import com.jaf.framework.core.model.BaseEntity;
import com.jaf.framework.util.Assert;

/**
 * 实体对象缓存时对应的 key 的生成器
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2015年11月15日
 * @since 1.0
 */
public class EntityCacheKeyGenerator implements CacheKeyGenerator<BaseEntity<?>> {

	@Override
	public String generateKey(BaseEntity<?> obj) {
		// 根据实体对象生成ID
		Assert.notNull(obj);
		return getKeyByClass(obj.getClass(), obj.getId().toString());
	}

	
	/**
	 * 根据实体类类型生成KEY
	 * @param clazz
	 * @param id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String getKeyByClass(Class<? extends BaseEntity> clazz, String id) {
		// className:id -> e.g. com.jaf.framework.core.model.User:48d919a3-82d0-469d-bd9a-560d3ee927b9
		return new StringBuilder().append(clazz.getName()).append(":").append(id).toString();
	}
	
}
