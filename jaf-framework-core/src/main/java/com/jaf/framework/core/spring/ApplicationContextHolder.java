package com.jaf.framework.core.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

/**
 * ApplicationContext 辅助工具类，也可以让该类实现 ApplicationContextAware 接口来获取注入的容器对象
 * 
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2015年10月1日
 * @since 1.0
 */
public class ApplicationContextHolder {
	
	public static ApplicationContext get() {
		return ContextLoader.getCurrentWebApplicationContext();
	}
	
	
	public static Object getBean(String beanName) {
		return get().getBean(beanName);
	}
	
	
	public static <T> T getBean(String name, Class<T> requiredType) {
		return get().getBean(name, requiredType);
	}
	
	
	public static <T> T getBean(Class<T> requiredType) {
		return get().getBean(requiredType);
	}
	
}
