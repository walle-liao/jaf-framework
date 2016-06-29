package com.jaf.framework.poi.excel.imports;

import java.util.Collection;

import com.jaf.framework.poi.excel.ColumnValueHandler;
import com.jaf.framework.poi.excel.DataProvider;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2016年6月29日
 * @since 1.0
 */
public class ImportSheetDataProvider<T> implements DataProvider<T> {

	@Override
	public Collection<T> getDatas() {
		return null;
	}

	@Override
	public boolean existsValueHandler(String key) {
		return false;
	}

	@Override
	public ColumnValueHandler getValueHandler(String key) {
		return null;
	}
	
}
