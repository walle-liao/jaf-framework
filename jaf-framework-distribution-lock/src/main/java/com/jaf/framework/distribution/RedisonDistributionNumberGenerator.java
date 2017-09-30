package com.jaf.framework.distribution;

import com.jaf.framework.distribution.key.DateKeyWrapper;
import org.redisson.core.RAtomicLong;

import java.util.concurrent.TimeUnit;

/**
 * @author: liaozhicheng
 * @Timestamp: 2017/07/19 10:42
 */
public class RedisonDistributionNumberGenerator implements DistributionNumberGenerator {

    private DistributionManager distributionManager;
    private final KeyWrapper dayKeyWrapper;

    public RedisonDistributionNumberGenerator() {
        this.dayKeyWrapper = new DateKeyWrapper();
    }

    @Override
    public long incrementAndGet(String key) {
        RAtomicLong atomicLong = distributionManager.getRedisson().getAtomicLong(key);
        atomicLong.expire(24 * 60 * 60, TimeUnit.SECONDS);   // 24h
        return atomicLong.incrementAndGet();
    }


    @Override
    public long incrementAndGetByDay(String key) {
        return incrementAndGet(this.dayKeyWrapper.wrap(key));
    }


}
