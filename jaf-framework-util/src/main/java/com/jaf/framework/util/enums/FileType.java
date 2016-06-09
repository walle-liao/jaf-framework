/*
 * Copyright 2012-2017 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
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
