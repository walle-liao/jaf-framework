package com.jaf.framework.poi.excel.export;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jaf.framework.poi.excel.ColumnValueHandler;
import com.jaf.framework.poi.excel.DataProvider;
import com.jaf.framework.poi.excel.model.ExcelSheet;
import com.jaf.framework.poi.excel.support.DefaultColumnValueHandler;
import com.jaf.framework.util.Assert;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年2月13日
 * @since 1.0
 * @see ExcelSheet
 */
public class ExportSheetDataProvider<T> implements DataProvider<T> {
	
	// 对应 sheet 页数据
	private final List<T> datas;
	
	// 对应的数据转换器 { key: fieldName, value: valueHandler }
	private final Map<String, ColumnValueHandler> valueHandlerMap = new HashMap<String, ColumnValueHandler>();
	
	public ExportSheetDataProvider(List<T> datas) {
		this.datas = datas;
	}
	
	public ExportSheetDataProvider<T> registValueHandler(String key, ColumnValueHandler valueHandler) {
		Assert.hasText(key);
		Assert.notNull(valueHandler);
		valueHandlerMap.put(key, valueHandler);
		return this;
	}
	
	@Override
	public List<T> getDatas() {
		return datas;
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
