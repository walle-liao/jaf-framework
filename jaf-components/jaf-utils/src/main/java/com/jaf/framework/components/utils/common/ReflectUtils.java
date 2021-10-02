package com.jaf.framework.components.utils.common;

import org.apache.commons.lang3.Validate;

import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Stream;

/**
 * 
 * 反射工具类，封装一些反射方法
 * 
 * @author liaozhicheng
 * @since 1.0
 */
public class ReflectUtils {

	public static Field[] getAllDeclaredFields(Class<?> type) {
		return recursionFields(type).toArray(new Field[0]);
	}

	public static  <T> T newInstance(Class<T> clazz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
		return clazz.getDeclaredConstructor().newInstance();
	}
	
	public static void makeAccessible(Field field) {
		if ((!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers()) ||
				Modifier.isFinal(field.getModifiers())) && !field.isAccessible()) {
			field.setAccessible(true);
		}
	}
	
	public static void setField(Field field, Object target, Object value) {
		try {
			field.set(target, value);
		}
		catch (IllegalAccessException ex) {
			throw new IllegalStateException("Unexpected reflection exception - " + ex.getClass().getName() + ": "
					+ ex.getMessage());
		}
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
		return fieldList.toArray(new Field[0]);
	}

	public static Field[] getSpecifiedAndSort(Class<?> type, Class<? extends Annotation> specify, Comparator<Field> comparator) {
		return Stream.of(getSpecified(type, specify)).sorted(comparator).toArray(Field[]::new);
	}
	
	
	private static List<Field> recursionFields(Class<?> type) {
		Validate.notNull(type, "Parameter 'type' is required");
		
		Class<?> clazz = type;
		
		List<Field> fieldList = new ArrayList<>();
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
