package com.jaf.framework.poi.excel.model;

import java.util.ArrayList;
import java.util.List;

import com.jaf.framework.poi.OfficeDocument;
import com.jaf.framework.poi.excel.support.SheetDataProvider;

/**
 * Excel文档
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年2月7日
 * @since 1.0
 */
public class ExcelDocument extends OfficeDocument {
	
	private static final String DEFAULT_SHEET_NAME = "Sheet";
	
	protected final List<ExcelSheet> sheets = new ArrayList<ExcelSheet>();
	
	public ExcelDocument(String fileName) {
		super(fileName);
	}
	
	public ExcelDocument createSheetWithAnnotation(Class<?> clazz, String sheetName) {
		return createSheetWithAnnotation(clazz, SheetDataProvider.newEmptyDatasProvider(), sheetName);
	}
	
	public ExcelDocument createSheetWithAnnotation(Class<?> clazz) {
		return createSheetWithAnnotation(clazz, SheetDataProvider.newEmptyDatasProvider(), getDefaultSheetName());
	}
	
	public ExcelDocument createSheetWithAnnotation(Class<?> clazz, SheetDataProvider datasProvider) {
		return createSheetWithAnnotation(clazz, datasProvider, getDefaultSheetName());
	}
	
	public ExcelDocument createSheetWithAnnotation(Class<?> clazz, SheetDataProvider datasProvider, String sheetName) {
		ExcelSheet sheet = ExcelSheet.createWithAnnotation(clazz, datasProvider, sheetName);
		this.addSheet(sheet);
		return this;
	}
	
	public ExcelDocument addSheet(ExcelSheet sheet) {
		this.sheets.add(sheet);
		return this;
	}

	public String getDefaultSheetName() {
		return new StringBuilder(DEFAULT_SHEET_NAME).append(sheets.size() + 1).toString();
	}
	
	public List<ExcelSheet> getSheets() {
		return sheets;
	}
	
}
