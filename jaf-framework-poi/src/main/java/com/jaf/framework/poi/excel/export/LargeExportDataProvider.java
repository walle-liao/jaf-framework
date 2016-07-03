package com.jaf.framework.poi.excel.export;

import java.util.Collection;

import org.apache.commons.collections4.CollectionUtils;

import com.jaf.framework.poi.excel.DataLoader;
import com.jaf.framework.poi.excel.support.AbstractDataProvider;

/**
 * 
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年7月2日
 * @since 1.0
 */
public class LargeExportDataProvider<T> extends AbstractDataProvider<T> {
	
	private static final int DEFAULT_PAGE_SIZE = 2000;
	
	private final DataLoader<T> dataLoader;
	
	// 分页查询时，每页查看的记录数
	private final int pageSize;
	
	// 总记录数
	private final int totalRecord;
	
	// 总页数
	private final int totalPages;
	
	// 当前页
	private int currentPage;
	
	// 是否已经加载到最后一页
	private boolean isLastPage;
	
	public LargeExportDataProvider(DataLoader<T> dataLoader, int pageSize) {
		this.dataLoader = dataLoader;
		this.pageSize = pageSize;
		this.totalRecord = dataLoader.selectTotalCount();
		this.currentPage = 1;
		this.totalPages = totalRecord / pageSize + ((totalRecord % pageSize == 0) ? 0 : 1);
	}
	
	public LargeExportDataProvider(DataLoader<T> dataLoader) {
		this(dataLoader, DEFAULT_PAGE_SIZE);
	}

	public boolean isLoadLast() {
		return isLastPage;
	}
	
	public Collection<T> loadPageDatas() {
		Collection<T> datas = dataLoader.load(currentPage, pageSize);
		if(CollectionUtils.isEmpty(datas) || currentPage >= totalPages) {
			isLastPage = true;
		} else {
			this.currentPage++;
		}
		return datas;
	}
	
	@Override
	public Collection<T> getDatas() {
		throw new UnsupportedOperationException("Unsupported operation, use #loadPageDatas method for load data.");
	}

	@Override
	public void fillDatas(Collection<?> datas) {
		throw new UnsupportedOperationException("Must fill data in constructor.");
	}
	
}
