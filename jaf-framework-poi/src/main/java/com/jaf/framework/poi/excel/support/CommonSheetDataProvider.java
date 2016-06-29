package com.jaf.framework.poi.excel.support;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.jaf.framework.poi.excel.ColumnValueHandler;
import com.jaf.framework.poi.excel.DataProvider;
import com.jaf.framework.poi.excel.model.ExcelSheet;
import com.jaf.framework.util.Assert;

/**
 * Sheet 页数据提供者
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年2月13日
 * @since 1.0
 * @see ExcelSheet
 */
public class CommonSheetDataProvider<T> implements DataProvider<T> {
	
	// 对应 sheet 页数据
	private Collection<T> datas;
	
	// 对应的数据转换器 { key: fieldName, value: valueHandler }
	private final Map<String, ColumnValueHandler> valueHandlerMap = new HashMap<String, ColumnValueHandler>();
	
	private CommonSheetDataProvider() {
	}
	
	public CommonSheetDataProvider(Collection<T> datas) {
		this.datas = datas;
	}
	
	@Override
	public DataProvider<T> registValueHandler(String key, ColumnValueHandler valueHandler) {
		Assert.hasText(key);
		Assert.notNull(valueHandler);
		valueHandlerMap.put(key, valueHandler);
		return this;
	}
	
	public static <T> DataProvider<T> newEmptyDataProvider(Class<T> clazz) {
		return new CommonSheetDataProvider<T>();
	}
	
	@Override
	public Collection<T> getDatas() {
		return datas;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void fillDatas(Collection<?> datas) {
		this.datas = (Collection<T>) datas;
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
