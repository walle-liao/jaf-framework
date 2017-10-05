package com.jaf.framework.core.service;

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
public interface BaseService<E extends BaseEntity<?>> {

	/**
	 * 插入实体
	 * 
	 * @param e
	 */
	void insertEntity(E e);

	/**
	 * 更新实体
	 * 
	 * @param e
	 */
	void updateById(E e);

	/**
	 * 插入或者更新，如果id属性有值则更新，否则插入
	 * 
	 * @param e
	 */
	void insertOrUpdate(E e);

	/**
	 * 更加ID查找实体
	 * 
	 * @param id
	 * @return 实体对象，找不到的话返回null
	 */
	<T> E findById(T id);

	/**
	 * 更加ID删除实体
	 * 
	 * @param id
	 */
	<T> void deleteById(T id);

	/**
	 * 批量删除实体
	 * 
	 * @param ids
	 */
	<T> void deleteByIds(T[] ids);

	/**
	 * 分页查询方法
	 * 
	 * @param pageNum
	 *            当前页码
	 * @param pageSize
	 *            当前页显示记录条数
	 * @param condition
	 *            查询条件
	 * @return 分页结果集
	 */
	PageInfo<E> pageQuery(int pageNum, int pageSize, Map<String, Object> condition);

	/**
	 * 重载的方法
	 * 
	 * @param pageNum
	 *            当前页码
	 * @param pageSize
	 *            当前页显示记录条数
	 * @param count
	 *            是否查询总条数（true:查询，false:不查询）
	 * @param condition
	 *            查询条件
	 * @return
	 */
	PageInfo<E> pageQuery(int pageNum, int pageSize, boolean count, Map<String, Object> condition);

	/**
	 * 只查询唯一的一个实体，没找到或找到多个都将抛出异常
	 * @param condition
	 * @return
	 */
	E selectOne(Map<String, Object> condition) throws UniquenessSelectException;

	/**
	 * 不使用分页，查找所有符合条件的记录
	 * @param condition 过滤条件
	 * @return
	 */
	List<E> listAll(Map<String, Object> condition);

}
