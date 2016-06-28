package com.jaf.framework.poi.excel;

/**
 * excel 导入/导出时列值处理器
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年2月7日
 * @since 1.0
 */
public interface ColumnValueHandler {
	
	/**
	 * 导入值处理
	 * @param value
	 * @return
	 */
	Object processImportValue(String value);
	
	/**
	 * 导出值处理
	 * @param value
	 * @return
	 */
	String processExportValue(Object value);
	
}
