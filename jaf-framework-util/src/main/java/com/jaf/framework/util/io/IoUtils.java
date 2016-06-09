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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

/**
 * 
 * Java I/O 常用操作的封装
 * 
 * @author liaozhicheng
 * @date 2012-6-12
 * @since 1.0
 */
public class IoUtils {
	
	/**
	 * Closes the given stream. The same as calling {@link InputStream#close()}, but errors while
	 * closing are silently ignored.
	 */
	public static void closeSilently(InputStream inputStream) {
		try {
			if (inputStream != null) {
				inputStream.close();
			}
		}
		catch (IOException ignore) {
			// Exception is silently ignored
		}
	}
	
	
	/**
	 * Closes the given stream. The same as calling {@link OutputStream#close()}, but errors while
	 * closing are silently ignored.
	 */
	public static void closeSilently(OutputStream outputStream) {
		try {
			if (outputStream != null) {
				outputStream.close();
			}
		}
		catch (IOException ignore) {
			// Exception is silently ignored
		}
	}
	
	
	public static void closeSilently(Reader reader) {
		try {
			if (reader != null) {
				reader.close();
			}
		}
		catch (IOException ignore) {
			
		}
	}
	
	
	public static void closeSilently(Writer writer) {
		try {
			if (writer != null) {
				writer.close();
			}
		}
		catch (IOException ignore) {
			
		}
	}
	
}
