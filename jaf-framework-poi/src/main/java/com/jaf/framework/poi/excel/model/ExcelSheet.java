package com.jaf.framework.poi.excel.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.jaf.framework.poi.excel.DataProvider;
import com.jaf.framework.poi.excel.SheetHeadBuilder;
import com.jaf.framework.poi.excel.support.AnnotationSheetHeadBuilder;
import com.jaf.framework.poi.excel.support.EmptyDataProvider;

/**
 * Excel文档里面的Sheet页
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年2月7日
 * @since 1.0
 */
public class ExcelSheet {
	
	// 数据关联的实体类
	private final Class<?> clazz;
	
	// 对应 sheet 页表头
	private final SheetHead head;
	
	// sheet 页名称
	private final String name;
	
	// 导入导出时出错的提示信息
	private final List<String> errorMsgHolder = new ArrayList<String>();
	
	// sheet 页数据提供者
	private final DataProvider<?> datasProvider;
	
	private ExcelSheet(SheetHeadBuilder builder, DataProvider<?> datasProvider, String sheetName) {
		this.clazz = builder.getTargetClazz();
		this.head = builder.build();
		this.name = sheetName;
		this.datasProvider = datasProvider;
	}
	
	public static ExcelSheet createWithAnnotation(Class<?> clazz, String sheetName) {
		return createWithAnnotation(clazz, null, sheetName);
	}
	
	public static ExcelSheet createWithAnnotation(Class<?> clazz, DataProvider<?> datasProvider, String sheetName) {
		datasProvider = datasProvider == null ? EmptyDataProvider.singleInstance() : datasProvider;
		ExcelSheet sheet = new ExcelSheet(new AnnotationSheetHeadBuilder(clazz), datasProvider, sheetName);
		return sheet;
	}
	
	public void fillDatas(List<?> datas) {
		// TODO 
	}
	
	public List<String> getErrorMsgHolder() {
		return errorMsgHolder;
	}

	public String getName() {
		return name;
	}
	
	public Class<?> getClazz() {
		return clazz;
	}
	
	public SheetHead getHead() {
		return head;
	}

	public Collection<?> getDatas() {
		return datasProvider.getDatas();
	}

	public DataProvider<?> getDatasProvider() {
		return datasProvider;
	}

}
