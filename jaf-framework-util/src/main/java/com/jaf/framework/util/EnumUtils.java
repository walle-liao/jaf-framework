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
