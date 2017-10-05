package com.jaf.framework.core.dao;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.jaf.framework.core.exception.UniquenessSelectException;
import com.jaf.framework.core.model.BaseEntity;


/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2015年9月5日
 * @since 1.0
 */
public interface BaseDao<E extends BaseEntity<?>> {
	
	void insertEntity(E e);

	void updateById(E e);

	void insertOrUpdate(E e);

	<T> E findById(T id);

	<T> void deleteById(T id);
	
	<T> void deleteByIds(T[] ids);
	
	PageInfo<E> pageQuery(int pageNum, int pageSize, Map<String, Object> condition);

	PageInfo<E> pageQuery(int pageNum, int pageSize, boolean count, Map<String, Object> condition);

	/**
	 * 只查询唯一的一个实体，没找到或找到多个都将抛出异常
	 * @param condition
	 * @return
	 */
	E selectOne(Map<String, Object> condition) throws UniquenessSelectException;

	List<E> listAll(Map<String, Object> condition);
	
}
