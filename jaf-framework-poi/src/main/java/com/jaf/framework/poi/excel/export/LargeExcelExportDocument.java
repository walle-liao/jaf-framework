package com.jaf.framework.poi.excel.export;

import java.io.OutputStream;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年6月30日
 * @since 1.0
 */
public class LargeExcelExportDocument extends ExcelExportDocument {
	
	private static final int DEFAULT_WINDOW_SIZE = 100;
	
	private int windowSize;

	public LargeExcelExportDocument(String fileName) {
		this(fileName,  DEFAULT_WINDOW_SIZE);
	}
	
	public LargeExcelExportDocument(String fileName, int windowSize) {
		super(fileName);
		this.windowSize = DEFAULT_WINDOW_SIZE;
	}

	public int getWindowSize() {
		return windowSize;
	}

	public void setWindowSize(int windowSize) {
		this.windowSize = windowSize;
	}
	
	public boolean doExport(OutputStream os) {
		return ExcelExportUtils.doLargeExport(os, this);
	}

}
