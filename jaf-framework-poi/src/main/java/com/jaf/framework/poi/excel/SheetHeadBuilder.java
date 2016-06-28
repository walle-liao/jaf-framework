package com.jaf.framework.poi.excel;

import com.jaf.framework.poi.excel.model.SheetHead;

/**
 * Sheet页表头构建器
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年2月8日
 * @since 1.0
 */
public interface SheetHeadBuilder {

	SheetHead build();
	
	Class<?> getTargetClazz();
	
}
