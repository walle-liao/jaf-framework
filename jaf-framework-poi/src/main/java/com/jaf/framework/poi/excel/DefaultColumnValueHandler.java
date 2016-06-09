package com.jaf.framework.poi.excel;

import java.util.Date;

import org.apache.commons.lang3.NotImplementedException;

import com.jaf.framework.util.DateUtils;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年2月7日
 * @since 1.0
 */
public abstract class DefaultColumnValueHandler implements ColumnValueHandler {
	
	private static final ColumnValueHandler INSTANCE = new DefaultColumnValueHandler() {
		
		@Override
		public Object processImportValue(String value) {
			return value;
		}

		@Override
		public String processExportValue(Object value) {
			if(value == null)
				return "";
			
			if(value instanceof Date)
				return DateUtils.formatYMD((Date) value);
			
			return value.toString();
		}
		
	};
	
	public static ColumnValueHandler getInstance() {
		return INSTANCE;
	}

	@Override
	public Object processImportValue(String value) {
		throw new NotImplementedException("processImportValue method not implemented");
	}

	@Override
	public String processExportValue(Object value) {
		throw new NotImplementedException("processExportValue method not implemented");
	}

}
