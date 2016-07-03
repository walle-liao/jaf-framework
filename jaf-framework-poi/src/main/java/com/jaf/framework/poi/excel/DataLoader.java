package com.jaf.framework.poi.excel;

import java.util.Collection;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年7月2日
 * @since 1.0
 */
public interface DataLoader<T> {

	/**
	 * 返回总记录数
	 * @return
	 */
	int selectTotalCount();
	
	/**
	 * 分页加载
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	Collection<T> load(int pageNum, int pageSize);
	
}
