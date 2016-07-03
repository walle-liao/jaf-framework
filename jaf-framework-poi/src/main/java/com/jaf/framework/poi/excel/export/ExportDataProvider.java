package com.jaf.framework.poi.excel.export;

import java.util.Collection;

import org.apache.commons.collections4.CollectionUtils;

import com.jaf.framework.poi.excel.support.AbstractDataProvider;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年7月2日
 * @since 1.0
 */
public class ExportDataProvider<T> extends AbstractDataProvider<T> {

	// 对应 sheet 页数据
	private final Collection<T> datas;
	
	public ExportDataProvider(Collection<T> datas) {
		this.datas = CollectionUtils.unmodifiableCollection(datas);
	}
	
	@Override
	public Collection<T> getDatas() {
		return datas;
	}

	@Override
	public void fillDatas(Collection<?> datas) {
		throw new UnsupportedOperationException("Must fill data in constructor.");
	}

}
