package com.jaf.framework.distribution;


/**
 * 分布式锁/容器名称的包装器
 * 用于支持例如多个城市部署在同一组服务器时，不同城市不同name
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年1月25日
 * @since 1.0
 */
public interface NameWrap {

	String wrap(String name);

}
