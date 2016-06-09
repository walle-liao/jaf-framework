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
package com.jaf.framework.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.jaf.framework.util.io.IoUtils;

/**
 * 
 * TODO
 * 
 * @author liaozhicheng
 * @date 2012-6-11
 * @since 1.0
 */
public class PropertiesUtils {
	
	// 工具类不需要实例化
	private PropertiesUtils() {
	}
	
	
	public static Properties loadProperties(final String propertieFileName) throws IOException {
		Assert.hasText(propertieFileName, "Parameter 'propertieFileName' must not be null or empty");
		
		InputStream inStream = null;
		try {
			inStream = PropertiesUtils.class.getClassLoader()
					.getResourceAsStream(propertieFileName);
			
			Properties properties = new Properties();
			properties.load(inStream);
			return properties;
		}
		finally {
			IoUtils.closeSilently(inStream);
		}
	}
	
}
