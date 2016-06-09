package com.jaf.framework.poi.excel.export;

import java.io.OutputStream;
import java.util.List;

import com.jaf.framework.poi.excel.ExcelDocument;
import com.jaf.framework.poi.excel.SheetDataProvider;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年2月9日
 * @since 1.0
 */
public class ExcelExportDocument extends ExcelDocument {

	public ExcelExportDocument(String fileName) {
		super(fileName);
	}

	public <E> ExcelDocument createSheetWithAnnotation(Class<E> clazz, List<?> datas, String sheetName) {
		createSheetWithAnnotation(clazz, new SheetDataProvider(datas), sheetName);
		return this;
	}
	
	public <E> ExcelDocument createSheetWithAnnotation(Class<E> clazz, List<?> datas) {
		createSheetWithAnnotation(clazz, new SheetDataProvider(datas), getDefaultSheetName());
		return this;
	}
	
	public boolean doExport(OutputStream os) {
		return ExcelExportUtils.doExport(os, this);
	}
	
}
