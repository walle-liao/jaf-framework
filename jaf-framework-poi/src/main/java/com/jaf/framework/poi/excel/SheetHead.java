package com.jaf.framework.poi.excel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jaf.framework.util.ArrayUtils;

/**
 * 封装一个excel导入模板的表头
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2015年10月1日
 * @since 1.0
 */
public final class SheetHead {
	
	// excel 表头列文本
	private final List<String> headTitles = new ArrayList<String>();
	
	// 对应实体属性名称
	private final List<String> fieldNames = new ArrayList<String>();
	
	// key = fieldName, value = SheetHeadColumn
	private final Map<String, SheetHeadColumn> headColumnMap = new HashMap<String, SheetHeadColumn>();
	
	// 对应实体的属性列表
	private Field[] fields;
	
	public SheetHead appendColumn(SheetHeadColumn headColumn) {
		headTitles.add(headColumn.getTitle());
		fieldNames.add(headColumn.getFieldName());
		headColumnMap.put(headColumn.getFieldName(), headColumn);
		return this;
	}
	
	public List<String> getHeadTitles() {
		return headTitles;
	}
	
	public void setFields(Field[] fields) {
		this.fields = fields;
	}

	public Field[] getFields() {
		return fields;
	}
	
	public List<String> getFieldNames() {
		return fieldNames;
	}

	public Map<String, SheetHeadColumn> getHeadColumnMap() {
		return headColumnMap;
	}

	public String toString() {
		return ArrayUtils.toString(headTitles.toArray());
	}
	
}
