package com.jaf.framework.poi;

import com.jaf.framework.util.enums.FileType;
import com.jaf.framework.util.io.FileUtils;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年2月9日
 * @since 1.0
 */
public class OfficeDocument {
	
	// 文件名
	protected final String fileName;
	
	// 文件类型
	protected final FileType fileType;
	
	public OfficeDocument(String fileName) {
		this.fileName = fileName;
		this.fileType = FileUtils.getFileType(fileName);
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public FileType getFileType() {
		return fileType;
	}
	
}
