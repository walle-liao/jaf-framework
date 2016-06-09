package com.jaf.framework.poi.excel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jaf.framework.util.Assert;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年2月13日
 * @since 1.0
 * @see ExcelSheet
 */
public class SheetDataProvider {
	
	// 对应 sheet 页数据
	private List<?> datas;
	
	// 对应的数据转换器 { key: fieldName, value: valueHandler }
	private final Map<String, ColumnValueHandler> valueHandlerMap = new HashMap<String, ColumnValueHandler>();
	
	@SuppressWarnings("rawtypes")
	public static SheetDataProvider newEmptyDatasProvider() {
		List<?> emptyDatas = new ArrayList();
		return new SheetDataProvider(emptyDatas);
	}
	
	public SheetDataProvider(List<?> datas) {
		this.datas = datas;
	}
	
	public boolean existsValueHandler(String key) {
		return valueHandlerMap.containsKey(key);
	}
	
	public ColumnValueHandler getValueHandler(String key) {
		if (existsValueHandler(key)) {
			return valueHandlerMap.get(key);
		}
		return DefaultColumnValueHandler.getInstance();
	}
	
	public SheetDataProvider registValueHandler(String key, ColumnValueHandler valueHandler) {
		Assert.hasText(key);
		Assert.notNull(valueHandler);
		valueHandlerMap.put(key, valueHandler);
		return this;
	}
	
	public List<?> getDatas() {
		return datas;
	}
	
	public void setDatas(List<?> datas) {
		this.datas = datas;
	}

	public Map<String, ColumnValueHandler> getValueHandlerMap() {
		return valueHandlerMap;
	}
	
}
