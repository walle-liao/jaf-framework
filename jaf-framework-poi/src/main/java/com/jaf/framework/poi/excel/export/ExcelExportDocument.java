package com.jaf.framework.poi.excel.export;

import java.io.OutputStream;
import java.util.List;

import com.jaf.framework.poi.excel.model.ExcelDocument;

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

	public <E> ExcelDocument createSheetWithAnnotation(Class<E> clazz, List<E> datas, String sheetName) {
		createSheetWithAnnotation(clazz, new ExportSheetDataProvider<E>(datas), sheetName);
		return this;
	}
	
	public <E> ExcelDocument createSheetWithAnnotation(Class<E> clazz, List<E> datas) {
		createSheetWithAnnotation(clazz, new ExportSheetDataProvider<E>(datas), getDefaultSheetName());
		return this;
	}
	
	public boolean doExport(OutputStream os) {
		return ExcelExportUtils.doExport(os, this);
	}
	
}
