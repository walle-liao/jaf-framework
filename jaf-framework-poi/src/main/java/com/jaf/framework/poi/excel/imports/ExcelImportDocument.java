package com.jaf.framework.poi.excel.imports;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jaf.framework.poi.excel.model.ExcelDocument;
import com.jaf.framework.poi.excel.model.ExcelSheet;
import com.jaf.framework.util.Assert;

/**
 * Excel 导入文档
 * 支持多个 sheet 页同时导入，每个 sheet 页必须定义一个表头，支持不同 sheet 页不同表头
 * 表头的定义可以采用注解方式和普通的构造方式
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年2月9日
 * @since 1.0
 */
public class ExcelImportDocument extends ExcelDocument {
	
	private final String pathName;
	
	private final File importFile;
	
	public ExcelImportDocument(String pathName) {
		this(new File(pathName));
	}
	
	public ExcelImportDocument(File importFile) {
		super(importFile.getName());
		this.importFile = importFile;
		this.pathName = importFile.getAbsolutePath();
	}
	
	public boolean doImport() {
		Assert.notNull(importFile, "importFile must not null");
		
		InputStream is = null;
		try {
			is = new BufferedInputStream(new FileInputStream(importFile));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doImport(is);
	}
	
	public boolean doImport(InputStream is) {
		return ExcelImportUtils.doImport(is, this);
	}
	
	public String getPathName() {
		return pathName;
	}

	public File getImportFile() {
		return importFile;
	}

	public List<String> getErrorMsgHolder() {
		List<String> errorMsgHolder = new ArrayList<String>();
		for(ExcelSheet sheet : sheets) {
			errorMsgHolder.addAll(sheet.getErrorMsgHolder());
		}
		return errorMsgHolder;
	}
	
	public Map<Integer, List<?>> getAllSheetDatas() {
		Map<Integer, List<?>> allSheetDatas = new HashMap<Integer, List<?>>();
		for(int i = 0; i < sheets.size(); i++) {
			allSheetDatas.put(Integer.valueOf(i), sheets.get(i).getDatas());
		}
		return allSheetDatas;
	}
	
	@SuppressWarnings("unchecked")
	public <E> List<E> getSheetDatas(int sheetIndex) {
		return (List<E>) sheets.get(sheetIndex).getDatas();
	}
	
	@SuppressWarnings("unchecked")
	public <E> List<E> getDefaultDatas() {
		return (List<E>) sheets.get(0).getDatas();
	}
	
}