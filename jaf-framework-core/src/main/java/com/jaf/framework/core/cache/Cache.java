package com.jaf.framework.core.cache;


/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2015年6月28日
 * @since 1.0
 */
public interface Cache {
	
	/**
	 * 将数据保存到cache服务器，如果保存成功则返回true,如果cache服务器存在同样的key，则替换之
	 * @param key 键
	 * @param exp 失效时间（单位：秒；0：表示永不失效）
	 * @param value 值
	 * @return
	 */
	boolean set(String key, int exp, Object value);
	
	
	/**
	 * 将数据添加到cache服务器,如果保存成功则返回true,如果cache服务器存在同样key，则返回false
	 * @param key
	 * @param exp
	 * @param value
	 * @return
	 */
	boolean add(String key, int exp, Object value);
	
	
	/**
	 * 将数据替换cache服务器中相同的key,如果保存成功则返回true,如果cache服务器不存在同样key，则返回false
	 * @param key
	 * @param exp
	 * @param value
	 * @return
	 */
	boolean replace(String key, int exp, Object value);
	
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	boolean delete(String key);
	
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	Object get(String key);
	
}
