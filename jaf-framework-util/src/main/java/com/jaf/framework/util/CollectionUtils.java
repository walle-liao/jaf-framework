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
