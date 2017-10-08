package com.jaf.framework.core.common.validate;

/**
 *
 * @author liaozhicheng.cn@163.com
 * @since 1.0
 */
public interface Validator<T> {

    Result verify(T target);

}
