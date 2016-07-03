package com.jaf.framework.poi.excel.export;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.ReflectionUtils;

import com.jaf.framework.poi.excel.ColumnValueHandler;
import com.jaf.framework.poi.excel.DataProvider;
import com.jaf.framework.poi.excel.model.ExcelSheet;
import com.jaf.framework.poi.excel.model.SheetHead;
import com.jaf.framework.poi.excel.model.SheetHeadColumn;
import com.jaf.framework.util.Assert;
import com.jaf.framework.util.CollectionUtils;
import com.jaf.framework.util.enums.FileType;

/**
 * excel 导出工具，支持导出多个 sheet 页
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年2月7日
 * @since 1.0
 */
class ExcelExportUtils {
	
	public static boolean doExport(OutputStream os, ExcelExportDocument excelDocument) {
		Assert.notNull(excelDocument);
		
		FileType fileType = excelDocument.getFileType();
		switch (fileType) {
			case EXCEL97:
				return doExport97(os, excelDocument);
				
			case EXCEL07:
				return doExport07(os, excelDocument);
				
			default:
				throw new IllegalArgumentException("error fileType : " + fileType);
		}
	}

	public static boolean doLargeExport(OutputStream os, LargeExcelExportDocument excelDocument) {
		Assert.notNull(excelDocument);
		
		SXSSFWorkbook sxssfWorkbook = null;
		try {
			sxssfWorkbook = new SXSSFWorkbook(excelDocument.getWindowSize());
			
			List<ExcelSheet> sheets = excelDocument.getSheets();
			Assert.isTrue(sheets.size() > 0);
			
			for(int i = 0; i < sheets.size(); i++) {
				ExcelSheet excelSheet = sheets.get(i);
				Sheet sheet = sxssfWorkbook.createSheet(excelSheet.getName());
				buildSheetHead(sxssfWorkbook, excelSheet, sheet);
				buildLargeSheetBody(excelSheet, sheet);
			}
			sxssfWorkbook.write(os);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (sxssfWorkbook != null)
				sxssfWorkbook.dispose();
		}
		return true;
	}
	
	private static boolean doExport97(OutputStream os, ExcelExportDocument excelDocument) {
		HSSFWorkbook hssfWorkbook = null;
		
		try {
			hssfWorkbook = new HSSFWorkbook();
			writeSheets(hssfWorkbook, excelDocument);
			hssfWorkbook.write(os);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (hssfWorkbook != null)
					hssfWorkbook.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	private static boolean doExport07(OutputStream os, ExcelExportDocument excelDocument) {
		XSSFWorkbook xssfWorkbook = null;
		try {
			xssfWorkbook = new XSSFWorkbook();
			writeSheets(xssfWorkbook, excelDocument);
			xssfWorkbook.write(os);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (xssfWorkbook != null) {
				try {
					xssfWorkbook.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}
	
	private static void writeSheets(Workbook workbook, ExcelExportDocument excelDocument) {
		List<ExcelSheet> sheets = excelDocument.getSheets();
		Assert.isTrue(sheets.size() > 0);
		
		for(int i = 0; i < sheets.size(); i++) {
			ExcelSheet excelSheet = sheets.get(i);
			Sheet sheet = workbook.createSheet(excelSheet.getName());
			writeSheet(workbook, excelSheet, sheet);
		}
	}
	
	private static void writeSheet(Workbook workbook, ExcelSheet excelSheet, Sheet sheet) {
		try {
			buildSheetHead(workbook, excelSheet, sheet);
			buildSheetBody(excelSheet, sheet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 生成表头
	private static void buildSheetHead(Workbook workbook, ExcelSheet excelSheet, Sheet sheet) {
		SheetHead head = excelSheet.getHead();
		List<String> titles = head.getHeadTitles();
		final Row row = sheet.createRow(0);
		for (int i = 0; i < titles.size(); i++) {
			final Cell cell = row.createCell(i);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellValue(titles.get(i));
			
			CellStyle style = workbook.createCellStyle();
			style.setAlignment(CellStyle.ALIGN_LEFT);
			cell.setCellStyle(style);
		}	
	}
	
	private static void buildSheetBody(ExcelSheet excelSheet, Sheet sheet) throws IllegalArgumentException, IllegalAccessException {
		Collection<?> datas = excelSheet.getDatas();
		if(CollectionUtils.isEmpty(datas))
			return ;
		
		Iterator<?> iterator = datas.iterator();
		int rowNum = 1;  // 从第二行开始插入数据
		while (iterator.hasNext()) {
			Object data = iterator.next();
			buildRow(excelSheet, sheet, data, rowNum);
			rowNum++;
		}
	}
	
	private static void buildLargeSheetBody(ExcelSheet excelSheet, Sheet sheet) 
			throws IllegalArgumentException, IllegalAccessException {
		final LargeExportDataProvider<?> datasProvider = (LargeExportDataProvider<?>) excelSheet.getDatasProvider();
		int rowNum = 1;  // 从第二行开始插入数据
		do {
			Collection<?> datas = datasProvider.loadPageDatas();
			Iterator<?> iterator = datas.iterator();
			while (iterator.hasNext()) {
				Object data = iterator.next();
				buildRow(excelSheet, sheet, data, rowNum);
				rowNum++;
			}
		} while(!datasProvider.isLoadLast());
	}
	
	
	private static void buildRow(ExcelSheet excelSheet, Sheet sheet, Object data, int rowNum) 
			throws IllegalArgumentException, IllegalAccessException {
		final SheetHead head = excelSheet.getHead();
		final DataProvider<?> datasProvider = excelSheet.getDatasProvider();
		final Map<String, SheetHeadColumn> headColumnMap = head.getHeadColumnMap();
		final Field[] fields = head.getFields();
		
		Row row = sheet.createRow(rowNum);
		for(int j = 0; j < fields.length; j++) {
			Field field = fields[j];
			String cellValue = handCellValue(field, data, datasProvider);
			
			// 创建单元格
			Cell cell = row.createCell(j);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellValue(cellValue);
			
			// 设置列宽，只要设置一次就可以了
			SheetHeadColumn headColumn = headColumnMap.get(field.getName());
			sheet.setColumnWidth(j, headColumn.getWidth());
		}
	}
	
	private static String handCellValue(Field field, Object data, DataProvider<?> datasProvider) 
			throws IllegalArgumentException, IllegalAccessException {
		// 处理单元格的值
		String cellValue = "";
		final String fieldName = field.getName();
		if(datasProvider.existsValueHandler(fieldName)) {
			ColumnValueHandler valueHandler = datasProvider.getValueHandler(fieldName);
			cellValue = valueHandler.processExportValue(data);
		} else {
			ReflectionUtils.makeAccessible(field);
			Object fieldValue = field.get(data);
			if(fieldValue != null)
				cellValue = fieldValue.toString();
		}
		return cellValue;
	}
	
}
