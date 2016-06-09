package com.jaf.framework.core.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jaf.framework.core.dao.BaseDao;
import com.jaf.framework.core.mapper.BaseMapper;
import com.jaf.framework.core.model.BaseEntity;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2015年9月5日
 * @since 1.0
 */
public abstract class BaseDaoImpl<E extends BaseEntity<?>> implements BaseDao<E> {
	
	private static final Logger LOG = Logger.getLogger(BaseDao.class);
	
	
	@Override
	public void insertEntity(E e) {
		if (e == null) {
			LOG.error("insert entity can not be null");
		}
		
		if(e.idIsEmpty()) {
			e.generateId();
		}
		getMapper().insertEntity(e);
	}
	
	
	@Override
	public void updateById(E e) {
		if (e == null) {
			LOG.error("updateById entity can not be null");
		}
		if (e.getId() == null) {
			LOG.error("updateById entity id can not be null");
		}
		
		getMapper().updateById(e);
	}
	
	
	@Override
	public void insertOrUpdate(E e) {
		if(e.idIsEmpty()) {
			insertEntity(e);
		} else {
			updateById(e);
		}
	}


	@Override
	public <T> E findById(T id) {
		if (id == null) {
			LOG.error("findById id can not be null");
		}
		
		return getMapper().findById(id);
	}
	
	
	@Override
	public <T> void deleteById(T id) {
		if (id == null) {
			LOG.error("deleteById id can not be null");
		}
		
		getMapper().deleteById(id);
	}
	
	
	@Override
	public <T> void deleteByIds(T[] ids) {
		for (T id : ids) {
			deleteById(id);
		}
	}
	
	
	@Override
	public PageInfo<E> pageQuery(int pageNum, int pageSize, Map<String, Object> condition) {
		return pageQuery(pageNum, pageSize, true, condition);
	}
	
	
	@Override
	public PageInfo<E> pageQuery(int pageNum, int pageSize, boolean count,
			Map<String, Object> condition) {
		PageHelper.startPage(pageNum, pageSize, count);
		
		Page<E> page = getMapper().pageQuery(condition);
		return new PageInfo<E>(page);
	}
	
	
	@Override
	public List<E> listAll(Map<String, Object> condition) {
		return getMapper().pageQuery(condition).getResult();
	}


	protected abstract BaseMapper<E> getMapper();
	
}
