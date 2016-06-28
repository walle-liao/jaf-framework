package com.jaf.framework.poi.excel;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年2月9日
 * @since 1.0
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ColumnHead {
	
	/**
	 * 标识属性对应的 excel 表头列的名称
	 * 
	 * @return
	 */
	String title();
	
	/**
	 * 定义execl表头的列宽
	 * @return
	 */
	int width() default 10;

}
