package com.jaf.framework.distribution;

import org.junit.runner.RunWith;
import org.redisson.Config;
import org.redisson.Redisson;
import org.redisson.RedissonClient;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class BaseTest {

	
	protected RedissonClient createNewRedissonClient() {
		Config config = new Config();
		config.useSingleServer().setAddress("192.168.0.42:8888")
				.setConnectionPoolSize(10);
		return Redisson.create(config);
	}
	
}
