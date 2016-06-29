package com.jaf.framework.poi.excel.support;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.NotImplementedException;

import com.jaf.framework.poi.excel.ColumnValueHandler;
import com.jaf.framework.poi.excel.DataProvider;

/**
 * 空数据提供
 * 
 * @author liaozhicheng
 * @date 2016年6月29日
 * @since 1.0
 */
public class EmptyDataProvider implements DataProvider<Object> {
	
	private static class EmptyDataProviderHolder {
		private static final EmptyDataProvider INSTANCE = new EmptyDataProvider();
	}
	
	public static EmptyDataProvider singleInstance() {
		return EmptyDataProviderHolder.INSTANCE;
	}
	
	@Override
	public boolean existsValueHandler(String key) {
		return false;
	}

	@Override
	public Collection<Object> getDatas() {
		return new ArrayList<Object>(0);
	}

	@Override
	public ColumnValueHandler getValueHandler(String key) {
		throw new NotImplementedException("");
	}
	
}
