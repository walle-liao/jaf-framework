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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import com.jaf.framework.util.Assert;

/**
 * <p>Title: ZipFileUtils</p>
 * <p>Description: TODO</p>
 * <p>Copyright: Copyright 2014</p>
 * @author longpanhua@purple-river.com
 * @date 2014年11月9日
 * @since 1.0
 */
public class ZipFileUtils {
	
	
	private static final String GBK_ENCOD = "gbk";
	
	
	/**
	 * 将指定的几个文件压缩到压缩包
	 * @param zipFileAbsoluteName 生成的压缩包的绝对路径文件名 eg.d:/temp/***.zip
	 * @param inZipFileAbsoluteName 需要压缩到压缩包里面的文件绝对路径文件名 eg. {"d:/temp/1.txt", "d:/temp/2.txt", ...} 
	 */
	public static void compression(String zipFileAbsoluteName, String[] inZipFileAbsoluteName) {
		Assert.hasText(zipFileAbsoluteName);
		Assert.isTrue(inZipFileAbsoluteName.length > 0);
		
		List<File> inZipFiles = new ArrayList<File>();
		for(String fileName : inZipFileAbsoluteName) {
			File tempFile = new File(fileName);
			inZipFiles.add(tempFile);
		}
		compression(zipFileAbsoluteName, inZipFiles);
	}
	
	
	public static void compression(String zipFileAbsoluteName, List<File> inZipFiles) {
		Assert.hasText(zipFileAbsoluteName);
		
		ZipOutputStream zipOut = null;
		InputStream is = null;
		try {
			zipOut = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(new File(zipFileAbsoluteName))));
			zipOut.setEncoding(GBK_ENCOD);
			
			for(File tempFile : inZipFiles) {
				if(tempFile.exists()) {
					String zipEntryName = tempFile.getName();
					ZipEntry zipEntity = new ZipEntry(zipEntryName);
					zipOut.putNextEntry(zipEntity);
					
					is = new FileInputStream(tempFile);
					int i = 0;
					byte[] b = new byte[1024 * 10];
					while((i = is.read(b)) > 0) {
						zipOut.write(b, 0, i);
					}
					is.close();
					zipOut.closeEntry();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(is != null) {
				IoUtils.closeSilently(is);
			}
			if(zipOut != null) {
				try {
					zipOut.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public static void compressionDir(String zipFileAbsoluteName, String inZipDirAbsoluteName) {
		Assert.hasText(zipFileAbsoluteName);
		Assert.hasText(inZipDirAbsoluteName);
		
		ZipOutputStream zipOut = null;
		try {
			zipOut = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(new File(zipFileAbsoluteName))));
			zipOut.setEncoding(GBK_ENCOD);
			File dir = new File(inZipDirAbsoluteName);
			handleDir(dir, zipOut);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(zipOut != null) {
				try {
					zipOut.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	private static void handleDir(File dir, ZipOutputStream zipOut) throws Exception {
		File[] files = dir.listFiles();
		
		InputStream is = null;
		
		try {
			// TODO 这里目录压缩会存在问题，后续有空再优化
			if(files.length == 0) {
				zipOut.putNextEntry(new ZipEntry(dir.getName() + "/"));
				zipOut.closeEntry(); 
			} else {
				for(File file : files) {
					if(file.isDirectory()) {
						// 如果是个目录，递归
						handleDir(file, zipOut);
					} else {
						zipOut.putNextEntry(new ZipEntry(file.getName()));
						is = new FileInputStream(file);
						int i = 0;
						byte[] b = new byte[1024 * 10];
						while((i = is.read(b)) > 0) {
							zipOut.write(b, 0, i);
						}
						is.close();
						zipOut.closeEntry();
					}
				}
			}
		} finally {
			if(is != null) {
				IoUtils.closeSilently(is);
			}
		}
	}
	
	
	// decompression
	
	/**
	 * 解压zip文件
	 * @param zipFilename
	 * @param dir
	 * @throws Exception
	 */
	public static String upZipFile(String zipFilename, String dir) {
		ZipInputStream zin = null;
		OutputStream os = null;
		String fileName = "";
		try {
			zin = new ZipInputStream(new FileInputStream(zipFilename));
			java.util.zip.ZipEntry ze = null;
			byte[] buf = new byte[1024];
			while ((ze = zin.getNextEntry()) != null) {
				if (ze.getName().endsWith(".template.html")) {
					fileName = ze.getName();
					os = new BufferedOutputStream(new FileOutputStream(getRealFileName(dir, ze.getName())));
					int readLen = 0;
					while ((readLen = zin.read(buf, 0, 1024)) != -1) {
						os.write(buf, 0, readLen);
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IoUtils.closeSilently(os);
			IoUtils.closeSilently(zin);
		}
		return dir + fileName;
	}
	
	
	public static File getRealFileName(String baseDir, String absFileName) {
		// 判断是否有下级目录,如果没有则将该文件直接new出来
		String[] dirs = absFileName.split("/");
		File ret = new File(baseDir);
		if (!ret.exists()) {
			ret.mkdir();
		}
		// 有下级目录则先创建目录，再创建文件
		if (dirs.length > 1) {
			for (int i = 0; i < dirs.length - 1; i++) {
				ret = new File(ret, dirs[i]);
			}
			if (!ret.exists())
				ret.mkdirs();
			ret = new File(ret, dirs[dirs.length - 1]);
			return ret;
		}
		return new File(ret, absFileName);
	}
	
}
