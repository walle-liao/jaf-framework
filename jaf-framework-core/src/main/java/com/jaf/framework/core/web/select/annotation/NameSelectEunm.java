package com.jaf.framework.core.web.select.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 可命名的枚举到下拉框的映射注解
 * 
 * @author liaozhicheng
 * @date 2015-1-26
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface NameSelectEunm {

	String key();

	Class<? extends Enum<?>> selectEnum();

}
