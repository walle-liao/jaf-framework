package com.jaf.framework.distribution.sequence;

import com.jaf.framework.distribution.DistributionManager;
import com.jaf.framework.distribution.key.KeyWrapper;
import com.jaf.framework.distribution.key.DateKeyWrapper;
import org.apache.commons.lang3.Validate;
import org.redisson.core.RAtomicLong;

import java.util.concurrent.TimeUnit;

/**
 * @author: liaozhicheng
 * @Timestamp: 2017/07/19 10:42
 */
public class RedisonDistributionSequenceGenerator implements DistributionSequenceGenerator {

    private DistributionManager distributionManager;
    private final KeyWrapper dayKeyWrapper;

    public RedisonDistributionSequenceGenerator() {
        this.dayKeyWrapper = new DateKeyWrapper();
    }

    @Override
    public long incrementAndGet(String key) {
        Validate.notEmpty(key);
        RAtomicLong atomicLong = distributionManager.getRedisson().getAtomicLong(key);
        atomicLong.expire(24 * 60 * 60, TimeUnit.SECONDS);   // 24h
        return atomicLong.incrementAndGet();
    }

    @Override
    public long incrementAndGet(String key, KeyWrapper keyWrapper) {
        Validate.notNull(keyWrapper);
        return this.incrementAndGet(keyWrapper.wrap(key));
    }

    @Override
    public long incrementAndGetByDay(String key) {
        return incrementAndGet(this.dayKeyWrapper.wrap(key));
    }

    public void setDistributionManager(DistributionManager distributionManager) {
        this.distributionManager = distributionManager;
    }
}
