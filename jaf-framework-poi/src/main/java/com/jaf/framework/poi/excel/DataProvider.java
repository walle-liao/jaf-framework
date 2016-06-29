package com.jaf.framework.poi.excel;

import java.util.Collection;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2016年6月29日
 * @since 1.0
 */
public interface DataProvider<T> {

	Collection<T> getDatas();
	
	/**
	 * 填充数据
	 * @param datas
	 */
	void fillDatas(Collection<?> datas);
	
	/**
	 * 对应的 key 是否存在值处理器
	 * @param key
	 * @return
	 */
	boolean existsValueHandler(String key);
	
	/**
	 * 注册一个值处理器
	 * @param key
	 * @param valueHandler
	 * @return
	 */
	DataProvider<T> registValueHandler(String key, ColumnValueHandler valueHandler);
	
	/**
	 * 获取值处理器
	 * @param key
	 * @return
	 */
	ColumnValueHandler getValueHandler(String key);
	
}
