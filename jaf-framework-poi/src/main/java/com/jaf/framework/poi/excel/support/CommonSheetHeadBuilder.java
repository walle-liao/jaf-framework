package com.jaf.framework.poi.excel.support;

import java.lang.reflect.Field;
import java.util.List;

import com.jaf.framework.poi.excel.SheetHeadBuilder;
import com.jaf.framework.poi.excel.model.SheetHead;
import com.jaf.framework.poi.excel.model.SheetHeadColumn;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年2月8日
 * @since 1.0
 */
public class CommonSheetHeadBuilder implements SheetHeadBuilder {

	private final Class<?> targetClazz;
	private final SheetHead head;
	
	public CommonSheetHeadBuilder(Class<?> targetClazz) {
		this.targetClazz = targetClazz;
		this.head = new SheetHead();
	}
	
	public CommonSheetHeadBuilder append(String display, String name, Integer width) {
		head.appendColumn(new SheetHeadColumn(display, name, width * SheetHeadColumn.DEFAULT_MULTIPLE));
		return this;
	}
	
	public CommonSheetHeadBuilder append(String display, String name) {
		this.append(display, name, SheetHeadColumn.DEFAULT_COLUMN_WIDTH);
		return this;
	}
	
	@Override
	public SheetHead build() {
		final List<String> fieldNames = head.getFieldNames();
		Field[] fields = new Field[fieldNames.size()];
		int i = 0;
		for(String fieldName : fieldNames) {
			try {
				fields[i++] = targetClazz.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		}
		head.setFields(fields);
		return head;
	}

	@Override
	public Class<?> getTargetClazz() {
		return targetClazz;
	}

}
