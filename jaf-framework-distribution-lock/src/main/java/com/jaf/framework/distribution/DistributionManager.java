package com.jaf.framework.distribution;

import org.redisson.Config;
import org.redisson.Redisson;
import org.redisson.RedissonClient;
import org.redisson.core.RList;
import org.redisson.core.RLock;
import org.redisson.core.RMap;
import org.redisson.core.RReadWriteLock;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.StringUtils;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @since 1.0
 */
public class DistributionManager implements InitializingBean {

	// redis 主机地址
	private String host;

	// redis 端口地址
	private int port;

	private KeyWrapper keyWrapper;

	private RedissonClient redisson;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Config config = new Config();
		config.useSingleServer().setAddress(this.host + ":" + this.port)
				.setConnectionPoolSize(50);
		redisson = Redisson.create(config);
	}
	
	public RLock getLock(String name) {
		return getLock(name, null);
	}
	
	/**
	 * 获取分布式锁
	 * @param name 锁的名称
	 * @param currentWrap 指定一个特殊的名称包装器，以覆盖默认配置
	 * @return
	 */
	public RLock getLock(String name, KeyWrapper currentWrap) {
		assertName(name);
		String wrapName = currentWrap != null ? currentWrap.wrap(name) : wrapName(name);
		return getRedisson().getLock(wrapName);
	}

	/**
	 * 获取分布式读写锁
	 * @param name
	 * @return
	 */
	public RReadWriteLock getReadWriteLock(String name) {
		return getReadWriteLock(name, null);
	}
	
	public RReadWriteLock getReadWriteLock(String name, KeyWrapper currentWrap) {
		assertName(name);
		String wrapName = currentWrap != null ? currentWrap.wrap(name) : wrapName(name);
		return getRedisson().getReadWriteLock(wrapName);
	}

	/**
	 * 分布式 List
	 * @param name
	 * @return
	 */
	public <V> RList<V> getList(String name) {
		assertName(name);
		final String wrapName = wrapName(name);
		return getRedisson().getList(wrapName);
	}

	/**
	 * 分布式 Map
	 * @param name
	 * @return
	 */
	public <K, V> RMap<K, V> getMap(String name) {
		assertName(name);
		final String wrapName = wrapName(name);
		return getRedisson().getMap(wrapName);
	}
	
	private boolean assertName(String name) {
		if (StringUtils.hasText(name))
			return true;
		throw new IllegalArgumentException("name must not be null or empty.");
	}
	

	public String wrapName(String name) {
		return keyWrapper != null ? keyWrapper.wrap(name) : name;
	}

	public KeyWrapper getKeyWrapper() {
		return keyWrapper;
	}

	public void setKeyWrapper(KeyWrapper keyWrapper) {
		this.keyWrapper = keyWrapper;
	}

	public RedissonClient getRedisson() {
		return redisson;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

}
