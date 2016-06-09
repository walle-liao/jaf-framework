package com.jaf.framework.core.model;

import org.apache.commons.lang3.StringUtils;

import com.jaf.framework.core.util.model.EntityUtils;

/**
 * 使用String作为ID类型的实体父类
 * 
 * @author liaozhicheng
 * @date 2015年9月24日
 * @since 1.0
 */
public class CommonEntity extends BaseEntity<String> {

	private static final long serialVersionUID = 482199976353047789L;
	
	
	public CommonEntity(){}
	
	
	public CommonEntity(String id) {
		super(id);
	}
	

	@Override
	public void generateId() {
		super.setId(EntityUtils.generateUuid());
	}

	@Override
	public boolean idIsEmpty() {
		return StringUtils.isEmpty(super.getId());
	}
	
	
	
}
