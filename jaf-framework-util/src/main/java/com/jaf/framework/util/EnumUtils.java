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

import java.lang.reflect.Field;

import com.jaf.framework.util.enums.FileType;

/**
 * 枚举操作工具类
 * 
 * @author liaozhicheng
 * @date 2015年10月2日
 * @since 1.0
 */
public final class EnumUtils extends org.apache.commons.lang3.EnumUtils {
	
	
	public static <E extends Enum<?>> E getEnumByPropertie(Class<E> enumClazz, String propertieName,
			String value) {
		Assert.notNull(enumClazz);
		Assert.hasText(propertieName);
		Assert.notNull(value);
		Assert.isTrue(enumClazz.isEnum());
		
		try {
			E[] values = enumClazz.getEnumConstants();
			Field field = enumClazz.getDeclaredField(propertieName);
			field.setAccessible(true);
			for (E e : values) {
				String ev = (String) field.get(e);
				if (value.equals(ev))
					return e;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static <E extends Enum<?>> E getEnumByText(Class<E> enumClazz, String value) {
		return getEnumByPropertie(enumClazz, "text", value);
	}
	
	
	public static void main(String[] args) {
		FileType ft = getEnumByPropertie(FileType.class, "suffix", "docx");
		System.out.println(ft);
	}
	
	
	private EnumUtils() {}
	
}
