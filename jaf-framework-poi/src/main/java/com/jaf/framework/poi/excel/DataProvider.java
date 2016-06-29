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
	
	boolean existsValueHandler(String key);
	
	ColumnValueHandler getValueHandler(String key);
}
