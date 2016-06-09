package com.jaf.framework.core.util.model;

import java.util.UUID;

import com.jaf.framework.core.model.BaseEntity;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2015年9月21日
 * @since 1.0
 */
public class EntityUtils {
	
	public static boolean isEmptyEntity(BaseEntity<?> entity) {
		return entity.getId() == null;
	}

	
	public static String generateUuid() {
		return UUID.randomUUID().toString();
	}
	
}
