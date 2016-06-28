package com.jaf.framework.poi.excel.model;

/**
 * excel Sheet页表头中的每一列
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2015年10月1日
 * @since 1.0
 */
public final class SheetHeadColumn {
	
	public static final int DEFAULT_MULTIPLE = 256;
	
	public static final Integer DEFAULT_COLUMN_WIDTH = Integer.valueOf(10);
	
	// 表头的名称
	private final String title;
	
	// 对应实体对象的属性名
	private final String fieldName;
	
	// 列宽
	private final Integer width;
	
	public SheetHeadColumn(String title, String fieldName) {
		this(title, fieldName, DEFAULT_COLUMN_WIDTH);
	}
	
	public SheetHeadColumn(String title, String fieldName, int width) {
		this.title = title;
		this.fieldName = fieldName;
		this.width = width * DEFAULT_MULTIPLE;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getFieldName() {
		return fieldName;
	}
	
	public Integer getWidth() {
		return width;
	}
	
}
