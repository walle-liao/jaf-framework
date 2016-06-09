package com.jaf.framework.core.web.select.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 该注解作用以枚举，被注解的枚举将可以自动转换成HTML页面的select DOM对象
 * 
 * @author liaozhicheng
 * @date 2015-1-26
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface Selectable {

	String value() default "";

}
