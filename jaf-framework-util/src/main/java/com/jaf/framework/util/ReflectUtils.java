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

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * 反射工具类，封装一些反射方法
 * 
 * @author liaozhicheng
 * @date 2012-6-12
 * @since 1.0
 */
public class ReflectUtils {
	
	public static Field[] getAllDeclaredFields(Class<?> type) {
		return recursionFields(type).toArray(new Field[0]);
	}
	
	
	/**
	 * 获取某个类包括其所有父类中私有属性
	 * 
	 * @param type
	 *            类型
	 * @param filters
	 *            需要过滤的属性
	 * @return
	 */
	public static Field[] getAllDeclaredFields(Class<?> type, String[] filters) {
		
		List<Field> fieldList = recursionFields(type);
		
		// 过滤掉某些不想要的属性
		if (filters != null && filters.length > 0) {
			for (String filter : filters) {
				Iterator<Field> fieldIterator = fieldList.iterator();
				while (fieldIterator.hasNext()) {
					Field field = fieldIterator.next();
					if (field.getName().equals(filter))
						fieldIterator.remove();
				}
			}
		}
		return fieldList.toArray(new Field[0]);
	}
	
	
	public static Field[] getAllDeclaredFields(Class<?> type, Class<? extends Annotation> filter) {
		
		List<Field> fieldList = recursionFields(type);
		
		if (filter != null) {
			Iterator<Field> fieldIterator = fieldList.iterator();
			while (fieldIterator.hasNext()) {
				Field field = fieldIterator.next();
				Annotation an = field.getAnnotation(filter);
				if (an != null) {
					fieldIterator.remove();
				}
			}
		}
		return fieldList.toArray(new Field[fieldList.size()]);
	}
	
	
	/**
	 * 获取指定类及其父类中某一注解标识的所有属性
	 * 
	 * @param type
	 * @param specify 指定注解
	 * @return
	 */
	public static Field[] getSpecified(Class<?> type, Class<? extends Annotation> specify) {
		
		List<Field> fieldList = recursionFields(type);
		
		if (specify != null) {
			Iterator<Field> fieldIterator = fieldList.iterator();
			while (fieldIterator.hasNext()) {
				Field field = fieldIterator.next();
				if(!field.isAnnotationPresent(specify)) {
					fieldIterator.remove();
				}
			}
		}
		
		return fieldList.toArray(new Field[fieldList.size()]);
	}
	
	
	private static List<Field> recursionFields(Class<?> type) {
		Assert.notNull(type, "Parameter 'type' is required");
		
		Class<?> clazz = type;
		
		List<Field> fieldList = new ArrayList<Field>();
		while (clazz != Object.class) {
			fieldList.addAll(Arrays.asList(clazz.getDeclaredFields()));
			clazz = clazz.getSuperclass();
		}
		
		return fieldList;
	}
	
	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Ignore {
		
	}

}
