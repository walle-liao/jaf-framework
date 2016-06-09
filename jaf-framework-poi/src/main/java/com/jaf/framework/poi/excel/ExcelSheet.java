package com.jaf.framework.poi.excel;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO
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
	private final SheetDataProvider datasProvider;
	
	private ExcelSheet(SheetHeadBuilder builder, SheetDataProvider datasProvider, String sheetName) {
		this.clazz = builder.getTargetClazz();
		this.head = builder.build();
		this.name = sheetName;
		this.datasProvider = datasProvider;
	}
	
	public static ExcelSheet createWithAnnotation(Class<?> clazz, String sheetName) {
		return createWithAnnotation(clazz, null, sheetName);
	}
	
	public static ExcelSheet createWithAnnotation(Class<?> clazz, SheetDataProvider datasProvider, String sheetName) {
		if(datasProvider == null)
			datasProvider = SheetDataProvider.newEmptyDatasProvider();
		ExcelSheet sheet = new ExcelSheet(new AnnotationSheetHeadBuilder(clazz), datasProvider, sheetName);
		return sheet;
	}
	
	public void fillDatas(List<?> datas) {
		this.datasProvider.setDatas(datas);
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

	public List<?> getDatas() {
		return datasProvider.getDatas();
	}

	public SheetDataProvider getDatasProvider() {
		return datasProvider;
	}
	
}
