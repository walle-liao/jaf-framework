package com.jaf.framework.core.mapper;

import java.util.Map;

import com.github.pagehelper.Page;
import com.jaf.framework.core.model.BaseEntity;


/**
 * mybatis 映射基类
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2015年9月5日
 * @since 1.0
 */
public interface BaseMapper<E extends BaseEntity<?>> {

	void insertEntity(E e);

	void updateById(E e);

	<T> void deleteById(T id);

	<T> void deleteByIds(T[] ids);
	
	Page<E> pageQuery(Map<String, Object> condition);
	
}
