package com.jaf.framework.core.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageInfo;
import com.jaf.framework.core.dao.BaseDao;
import com.jaf.framework.core.model.BaseEntity;
import com.jaf.framework.core.service.BaseService;
import com.jaf.framework.util.Assert;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2015年9月6日
 * @since 1.0
 */
public abstract class BaseServiceImpl<E extends BaseEntity<?>> implements BaseService<E> {
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void insertEntity(E e) {
		getDao().insertEntity(e);
	}
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateById(E e) {
		getDao().updateById(e);
	}
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void insertOrUpdate(E e) {
		Assert.notNull(e, "insertOrUpdate e must not null");
		getDao().insertOrUpdate(e);
	}


	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public <T> E findById(T id) {
		return getDao().findById(id);
	}
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public <T> void deleteById(T id) {
		getDao().deleteById(id);
	}
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public <T> void deleteByIds(T[] ids) {
		getDao().deleteByIds(ids);
	}
	
	
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public PageInfo<E> pageQuery(int pageNum, int pageSize, Map<String, Object> condition) {
		return getDao().pageQuery(pageNum, pageSize, condition);
	}
	
	
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public PageInfo<E> pageQuery(int pageNum, int pageSize, boolean count,
			Map<String, Object> condition) {
		return getDao().pageQuery(pageNum, pageSize, count, condition);
	}
	
	
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<E> listAll(Map<String, Object> condition) {
		return getDao().listAll(condition);
	}


	protected abstract BaseDao<E> getDao();
	
}
