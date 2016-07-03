package com.jaf.framework.poi.excel.imports;

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
public class ImportDataProvider<T> extends AbstractDataProvider<T> {

	private Collection<T> datas;
	
	@Override
	public Collection<T> getDatas() {
		return datas;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void fillDatas(Collection<?> datas) {
		this.datas = CollectionUtils.unmodifiableCollection((Collection<T>) datas);
	}
	
}
