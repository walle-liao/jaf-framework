package com.jaf.framework.util.enums;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2015年10月2日
 * @since 1.0
 */
public enum FileType {
	
	EXCEL97("xls"), EXCEL07("xlsx"), WORD97("doc"), WORD07("docx"), PDF("pdf"), TEXT("txt");
	
	private final String suffix;
	
	
	private FileType(String suffix) {
		this.suffix = suffix;
	}
	
	
	public String getSuffix() {
		return suffix;
	}
	
	
}
