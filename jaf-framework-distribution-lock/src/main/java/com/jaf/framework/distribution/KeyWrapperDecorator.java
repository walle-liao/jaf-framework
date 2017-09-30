package com.jaf.framework.distribution;

import io.netty.util.internal.StringUtil;

/**
 * @author: liaozhicheng.cn@163.com
 * @since 1.0
 */
public abstract class KeyWrapperDecorator implements KeyWrapper {

    protected KeyWrapper decorator;

    protected KeyWrapperDecorator(KeyWrapper decorator) {
        this.decorator = decorator;
    }

    protected abstract String wrapInner(String key);

    @Override
    public String wrap(String key) {
        if(StringUtil.isNullOrEmpty(key))
            throw new IllegalArgumentException("key can not be null or empty");

        return new StringBuilder().append(decorator.wrap(wrapInner(key))).toString();
    }
}
