package com.jaf.framework.distribution.key;

import org.apache.commons.lang3.Validate;

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
        Validate.notEmpty(key);
        return new StringBuilder().append(this.wrapInner(key)).append("_").append(decorator.wrap(key)).toString();
    }
}
