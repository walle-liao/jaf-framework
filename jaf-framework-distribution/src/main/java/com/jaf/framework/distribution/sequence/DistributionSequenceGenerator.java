package com.jaf.framework.distribution.sequence;

import com.jaf.framework.distribution.key.KeyWrapper;

/**
 * 分布式集群环境下生成唯一自增长序列号
 *
 * @author: liaozhicheng
 * @Timestamp: 2017/07/19 10:29
 */
public interface DistributionSequenceGenerator {

    /**
     * 集群环境下生成唯一自增系列号
     * @param key
     * @return
     */
    long incrementAndGet(String key);

    /**
     * 按规则生成唯一自增系列号
     * @param key
     * @param keyWrapper
     * @return
     */
    long incrementAndGet(String key, KeyWrapper keyWrapper);

    /**
     * 集群环境下生成唯一自增系列号（按天进行隔离生成）
     * @param key
     * @return
     */
    long incrementAndGetByDay(String key);

}
