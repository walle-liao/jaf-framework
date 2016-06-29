package com.jaf.framework.poi.excel.imports;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.ReflectionUtils;

import com.jaf.framework.poi.excel.ColumnValueHandler;
import com.jaf.framework.poi.excel.DataProvider;
import com.jaf.framework.poi.excel.model.ExcelSheet;
import com.jaf.framework.poi.excel.model.SheetHead;
import com.jaf.framework.util.Assert;
import com.jaf.framework.util.enums.FileType;
import com.jaf.framework.util.io.IoUtils;

/**
 * Execl 导入工具类
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年2月7日
 * @since 1.0
 */
class ExcelImportUtils {
	
	public static boolean doImport(InputStream is, ExcelImportDocument excelDocument) {
		Assert.notNull(excelDocument);
		
		FileType fileType = excelDocument.getFileType();
		switch (fileType) {
			case EXCEL97:
				return doImport97(is, excelDocument);
				
			case EXCEL07:
				return doImport07(is, excelDocument);
				
			default:
				throw new IllegalArgumentException("error fileType : " + fileType);
		}
	}
	
	private static boolean doImport97(InputStream is, ExcelImportDocument excelDocument) {
		HSSFWorkbook hssfWorkbook = null;
		try {
			hssfWorkbook = new HSSFWorkbook(is);
			return readSheets(hssfWorkbook, excelDocument);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (hssfWorkbook != null)
					hssfWorkbook.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			IoUtils.closeSilently(is);
		}
		return false;
	}
	
	private static boolean doImport07(InputStream is, ExcelImportDocument excelDocument) {
		XSSFWorkbook xssfWorkbook = null;
		try {
			xssfWorkbook = new XSSFWorkbook(is);
			return readSheets(xssfWorkbook, excelDocument);
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
			IoUtils.closeSilently(is);
		}
		return false;
	}
	
	private static boolean readSheets(Workbook workbook, ExcelImportDocument excelDocument) 
			throws InstantiationException, IllegalAccessException {
		boolean importSuccess = true;
		
		List<ExcelSheet> sheets = excelDocument.getSheets();
		for(int i = 0; i < sheets.size(); i++) {
			ExcelSheet excelSheet = sheets.get(i);
			Sheet sheet = null;
			String sheetName = excelSheet.getName();
			if(StringUtils.isNoneBlank(sheetName)) {
				// 优选按名称匹配
				sheet = workbook.getSheet(sheetName);
			} else {
				sheet = workbook.getSheetAt(i);
			}
			
			Assert.notNull(sheet);
			readSheet(excelSheet, sheet, excelSheet.getClazz());
		}
		return importSuccess;
	}
	
	private static <E> boolean readSheet(ExcelSheet excelSheet, Sheet sheet, Class<E> clazz)
			throws InstantiationException, IllegalAccessException {
		// 校验上传的 excel 文档模板是否正确，如果模板不正确则不解析
		SheetHead sheetHead = excelSheet.getHead();
		Row headRow = sheet.getRow(0);
		if (!verifyHeadRow(headRow, sheetHead, excelSheet.getErrorMsgHolder())) {
			return false;
		}
		
		// 逐行逐列解析 excel
		final Field[] fields = sheetHead.getFields();
		final DataProvider<?> datasProvider = excelSheet.getDatasProvider();
		final int lastRowNum = sheet.getLastRowNum();
		final int lastColumnNum = fields.length;
		
		List<E> result = new ArrayList<E>(lastRowNum);
		for (int rowNum = 1; rowNum <= lastRowNum; rowNum++) {
			Row row = sheet.getRow(rowNum);
			if (row == null)
				continue;
				
			E entity = clazz.newInstance();
			boolean emptyRow = true;
			for (int columnNum = 0; columnNum < lastColumnNum; columnNum++) {
				Cell cell = row.getCell(columnNum);
				if (cell == null)
					continue;
					
				cell.setCellType(Cell.CELL_TYPE_STRING);
				String cellVal = cell.getStringCellValue();
				if(StringUtils.isNoneBlank(cellVal)) {
					Field field = fields[columnNum];
					ReflectionUtils.makeAccessible(field);
					
					// 处理列的值，如果指定了列的值处理器
					ColumnValueHandler valueHandler = datasProvider.getValueHandler(field.getName());
					Object newVal = valueHandler.processImportValue(cellVal.trim());
					ReflectionUtils.setField(field, entity, newVal);
					emptyRow = false;
				}
			}
			
			if(!emptyRow)
				result.add(entity);
		}
		excelSheet.fillDatas(result);
		return true;
	}
	
	
	/**
	 * 校验上传的 excel 文档的模板是否正确
	 * @param headRow
	 * @param sheetHead
	 * @param errorMsgHolder
	 * @return
	 */
	private static boolean verifyHeadRow(Row headRow, SheetHead sheetHead, List<String> errorMsgHolder) {
		if (headRow == null) {
			errorMsgHolder.add("导入的 excel 未定义表头，请检查，正确的列顺序：" + sheetHead.toString());
			return false;
		}
			
		List<String> headTitles = sheetHead.getHeadTitles();
		int lastCellNum = headRow.getLastCellNum();
		if (lastCellNum != headTitles.size()) {
			// 表头列的数量和实体中定义的表头类的数量不一致
			errorMsgHolder.add("导入的 excel 表头长度和模板定义的长度不一致，请检查，正确的列顺序：" + sheetHead.toString());
			return false;
		}
		
		boolean verify = true;
		for (int i = 0; i < lastCellNum; i++) {
			Cell cell = headRow.getCell(i);
			String title = cell.getStringCellValue();
			String templeteTitle = headTitles.get(i);
			if (!templeteTitle.equals(title)) {
				verify = false;
				errorMsgHolder.add("导入的 excel 表头列定义和模板定义的不一致，请检查，正确的列顺序：" + sheetHead.toString());
				break;
			}
		}
		return verify;
	}
	
}
