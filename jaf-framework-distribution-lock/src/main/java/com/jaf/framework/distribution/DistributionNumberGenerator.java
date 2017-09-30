package com.jaf.framework.distribution;

/**
 * @author: liaozhicheng
 * @Timestamp: 2017/07/19 10:29
 */
public interface DistributionNumberGenerator {

    /**
     * 集群环境下生成唯一自增系列号
     * @param key
     * @return
     */
    long incrementAndGet(String key);

    /**
     * 集群环境下生成唯一自增系列号（按天进行隔离生成）
     * @param key
     * @return
     */
    long incrementAndGetByDay(String key);


}
