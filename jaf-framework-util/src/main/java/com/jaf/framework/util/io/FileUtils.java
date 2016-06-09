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
package com.jaf.framework.util.io;

import java.io.File;

import org.apache.commons.lang3.StringUtils;

import com.jaf.framework.util.Assert;
import com.jaf.framework.util.EnumUtils;
import com.jaf.framework.util.enums.FileType;

/**
 * 文件操作工具类，扩展自 <code>org.apache.commons.io.FileUtils</code>
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2015年10月2日
 * @since 1.0
 */
public final class FileUtils extends org.apache.commons.io.FileUtils {
	
	/**
	 * 给定一个文件名，获取该文件名的后缀
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFileSuffix(String fileName) {
		Assert.hasText(fileName);
		String suffix = "";
		int indexDot = fileName.lastIndexOf(".");
		if (indexDot > 0) {
			suffix = fileName.substring(indexDot + 1, fileName.length());
		}
		return suffix;
	}
	
	
	public static String getFileSuffix(File file) {
		Assert.notNull(file);
		return getFileSuffix(file.getName());
	}
	
	
	public static FileType getFileType(String fileName) {
		return EnumUtils.getEnumByPropertie(FileType.class, "suffix", getFileSuffix(fileName));
	}
	
	
	public static FileType getFileType(File file) {
		Assert.notNull(file);
		return getFileType(file.getName());
	}

	
	/**
	 * 删除某个源目录及其所有子目录下的指定目录或文件 
	 * 1.不区分删除的是目录还是文件，可同时删除文件和目录
	 * 2.采用可变参数形式，可同时删除多个目录或文件，采用 #deleteFileOrDir(sourceDir, dir1, file1, dir2);
	 * 3.如果不指定 targets 或者 targets == null，则默认删除源目录；即如果需要删除某个目录或文件可使用 {@link #deleteFileOrDir(String)}
	 * 
	 * @param sourceDir
	 *            源目录
	 * @param targets
	 *            需要删除的目录或文件
	 */
	public static void deleteFileOrDir(String sourceDir, String... targets) {
		if (StringUtils.isEmpty(sourceDir))
			return;

		File source = new File(sourceDir);
		if (!source.exists())
			return;

		// 如果不指定目标文件，则默认删除源目录
		if (targets == null || targets.length == 0) {
			FileUtils.deleteQuietly(source);
			return;
		}

		delete(source, targets);
	}

	// 递归删除源目录及其子目录下所有目标目录和文件
	private static void delete(File source, String... targets) {
		File[] files = source.listFiles();
		for (File file : files) {
			for (String target : targets) {
				if (StringUtils.isNotEmpty(target) && target.equals(file.getName())) {
					FileUtils.deleteQuietly(file);
				}
			}

			if (file.isDirectory())
				delete(file, targets);
		}
	}
	
	public static void main(String[] args) {
		deleteFileOrDir("F:/temp", ".svn", "vssver2.scc");
	}
	
	
	private FileUtils() {}
	
}
