package com.jaf.framework.core;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2015年6月27日
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public abstract class IntegrationTests {
	
	@Test
	public void test() {
	}
	
}
