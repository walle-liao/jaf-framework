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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2011-11-3
 * @since 1.0
 */
public class ArrayUtils {
	
	/**
	 * 该方法与Arrays#asList()方法不同的地方 
	 * <code>
	 * 	String[] array = null;
	 * 	List<String> list1 = Arrays.asList(array);  // throw NullPointerException
	 * 	List<String> list2 = ArrayUtils.asList(array);  // return new ArrayList();
	 * </code>
	 * 
	 * @param array
	 * @return
	 */
	public static <T> List<T> asList(T[] array) {
		List<T> result = new ArrayList<T>();
		
		if (array != null && array.length > 0)
			Collections.addAll(result, array);
		
		return result;
	}
	
	
	/**
	 * 将一个数组拼装成字符串形式返回，每个元素之间用','隔开 </br>
	 * <code>
	 * String[] str = { "11", "22" };  // "11,22"
	 * Boolean[] bs = new Boolean[] { true, false }; // "true,false"
	 * </code>
	 * 
	 * 注：该方法不支持基本数据类型的数组，如果是自定义的java类，则调用对象的{@link #toString()}
	 * 
	 * @param array
	 * @return
	 */
	public static <T> String toString(T[] array) {
		if (array == null || array.length == 0)
			return null;
		
		StringBuilder result = new StringBuilder();
		for (T t : array) {
			result.append(t).append(",");
		}
		result.deleteCharAt(result.length() - 1);
		return result.toString();
	}
	
}
