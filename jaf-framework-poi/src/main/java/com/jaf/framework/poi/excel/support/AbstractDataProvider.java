package com.jaf.framework.poi.excel.support;

import java.util.HashMap;
import java.util.Map;

import com.jaf.framework.poi.excel.ColumnValueHandler;
import com.jaf.framework.poi.excel.DataProvider;
import com.jaf.framework.util.Assert;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年7月2日
 * @since 1.0
 */
public abstract class AbstractDataProvider<T> implements DataProvider<T> {

	// 对应的数据转换器 { key: fieldName, value: valueHandler }
	private final Map<String, ColumnValueHandler> valueHandlerMap = new HashMap<String, ColumnValueHandler>();
	
	@Override
	public DataProvider<T> registValueHandler(String key, ColumnValueHandler valueHandler) {
		Assert.hasText(key);
		Assert.notNull(valueHandler);
		valueHandlerMap.put(key, valueHandler);
		return this;
	}
	
	@Override
	public boolean existsValueHandler(String key) {
		return valueHandlerMap.containsKey(key);
	}
	
	@Override
	public ColumnValueHandler getValueHandler(String key) {
		if (existsValueHandler(key)) {
			return valueHandlerMap.get(key);
		}
		return DefaultColumnValueHandler.getInstance();
	}
	
	public Map<String, ColumnValueHandler> getValueHandlerMap() {
		return valueHandlerMap;
	}
	
}
