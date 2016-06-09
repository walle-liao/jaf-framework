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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


/**
 * 集合操作工具类
 * 
 * @author liaozhicheng
 * @date 2015年3月28日
 * @since 1.0
 */
public class CollectionUtils {
	
	public static final boolean isEmpty(Collection<?> collection) {
		return collection == null || collection.isEmpty();
	}
	
	public static final boolean isNotEmpty(Collection<?> collection) {
		return collection != null && !collection.isEmpty();
	}
	
	
	public static final <K, V> HashMap<K, V> newHashMapInstance() {
		return new HashMap<K, V>();
	}
	
	
	public static final <K, V> Map<K, V> convertCollectionToMap(Collection<V> coll, ItemHandler<K, V> handler) {
		Map<K, V> map = new HashMap<K, V>();
		for(V item : coll) {
			map.put(handler.getKey(item), handler.getValue());
		}
		return map;
	}
	
	
	public abstract static class ItemHandler<K, V> {
		
		private V item;
		
//		public ItemHandler(V item) {
//			this.item = item;
//		}
		
		abstract K getKey(V item);
		
		V getValue() {
			return item;
		}
		
	}

	
}
