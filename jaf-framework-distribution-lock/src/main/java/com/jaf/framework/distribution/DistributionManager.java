package com.jaf.framework.distribution;

import org.redisson.Config;
import org.redisson.Redisson;
import org.redisson.RedissonClient;
import org.springframework.beans.factory.InitializingBean;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年1月25日
 * @since 1.0
 */
public class DistributionManager implements InitializingBean {
	
	// redis主机地址
	private String host;
	
	// redis端口地址
	private int port;
	
	private NameWrap nameWrap;
	
	private RedissonClient redisson;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Config config = new Config();
		config.useSingleServer().setAddress(this.host + ":" + this.port)
				.setConnectionPoolSize(1000);
		redisson = Redisson.create(config);
	}
	
	public NameWrap getNameWrap() {
		return nameWrap;
	}
	
	public void setNameWrap(NameWrap nameWrap) {
		this.nameWrap = nameWrap;
	}
	
	public RedissonClient getRedisson() {
		return redisson;
	}
	
}
