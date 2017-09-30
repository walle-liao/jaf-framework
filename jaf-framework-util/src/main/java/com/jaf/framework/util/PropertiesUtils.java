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
