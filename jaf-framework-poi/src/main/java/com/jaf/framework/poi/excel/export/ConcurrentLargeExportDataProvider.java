package com.jaf.framework.poi.excel.export;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import com.jaf.framework.poi.excel.DataLoader;
import com.jaf.framework.poi.excel.support.AbstractDataProvider;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年7月3日
 * @since 1.0
 */
public class ConcurrentLargeExportDataProvider<T> extends AbstractDataProvider<T> {
	
	// 默认的并发级别
	private final static int DEFAULT_CONCURRENCY_LEVEL = 5;
	
	private final DataLoader<T> dataLoader;
	
	// 分页查询时，每页查看的记录数
	private final int pageSize;
	
	// 总记录数
	private final int totalRecord;
	
	// 总页数
	private final int totalPages;
	
	// 当前页
	private AtomicInteger currentPage;
	
	// 是否已经加载到最后一页
	private volatile boolean isLastPage;
	
	
	private final Map<Integer, Collection<T>> map = new ConcurrentSkipListMap<Integer, Collection<T>>();
	
	// 并发级别
	private final int concurrencyLevel = DEFAULT_CONCURRENCY_LEVEL;
	
	public ConcurrentLargeExportDataProvider(DataLoader<T> dataLoader, int pageSize) {
		this.dataLoader = dataLoader;
		this.pageSize = pageSize;
		this.totalRecord = dataLoader.selectTotalCount();
		this.currentPage = new AtomicInteger(1);
		this.totalPages = totalRecord / pageSize + ((totalRecord % pageSize == 0) ? 0 : 1);
		
	}
	
	public void init() {
		ExecutorService executorService = Executors.newFixedThreadPool(DEFAULT_CONCURRENCY_LEVEL);
		for(int i = 0; i < concurrencyLevel; i++) {
			executorService.execute(new Runnable() {
				@Override
				public void run() {
					while(!isLoadLast()) {
						int pageNum = currentPage.getAndIncrement();
						if(pageNum > totalPages) {
							isLastPage = true;
							continue ;
						}
						Collection<T> datas = dataLoader.load(pageNum, pageSize );
						map.put(Integer.valueOf(pageNum), datas);
					}
				}
			});
		}
		executorService.shutdown();
	}

	public boolean isLoadLast() {
		return isLastPage;
	}
	
	@Override
	public Collection<T> getDatas() {
		throw new UnsupportedOperationException("");
	}

	@Override
	public void fillDatas(Collection<?> datas) {
		throw new UnsupportedOperationException("");
	}

}
