package com.jaf.framework.poi.excel.export;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

import com.jaf.framework.poi.excel.DataLoader;
import com.jaf.framework.poi.excel.DataProvider;
import com.jaf.framework.poi.excel.dto.UserAnnotationDto;
import com.jaf.framework.poi.excel.dto.UserDto;
import com.jaf.framework.poi.excel.support.CommonSheetDataProvider;
import com.jaf.framework.poi.excel.support.CommonSheetHeadBuilder;
import com.jaf.framework.poi.excel.support.DefaultColumnValueHandler;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年2月11日
 * @since 1.0
 */
public class ExcelExportTest {
	
	/**
	 * 简单的导出功能
	 * @throws IOException 
	 */
	@Test
	public void simpleExportTest() throws IOException {
		OutputStream os = null;
		try {
			final String fileName = "simpleExport.xls";
			File file = new File("d:/temp/test/" + fileName);
			os = new BufferedOutputStream(new FileOutputStream(file));
			
			// 创建 excel 导出文档
			ExcelExportDocument excel = new ExcelExportDocument(fileName);
			
			// 获取导出数据，并且装配到 DataProvider 接口
			List<UserDto> mockDatas = getMockDatas();
			DataProvider<UserDto> datasProvider = new CommonSheetDataProvider<UserDto>(mockDatas);
			
			// 特殊列值处理
			datasProvider.registValueHandler("age", new DefaultColumnValueHandler() {
				@Override
				public String processExportValue(Object value) {
					return ((UserDto) value).getAge() + "1";
				}
			});
			
			// 创建表头构建器
			CommonSheetHeadBuilder headBuilder = new CommonSheetHeadBuilder(UserDto.class);
			headBuilder.append("姓名", "name")
						.append("身份证号", "idCard", 20)
						.append("年龄", "age");
			
			// 创建 sheet 页，并且指定该 sheet 页的表头和数据
			excel.createSheet(headBuilder, datasProvider);
			
			// 执行导出
			excel.doExport(os);
		} finally {
			os.close();
		}
	}
	
	@Test
	public void simpleExportWithAnnotationTest() throws IOException {
		OutputStream os = null;
		try {
			final String fileName = "simpleExport2.xlsx"; // 导出07版本的 excel
			File file = new File("d:/temp/test/" + fileName);
			os = new BufferedOutputStream(new FileOutputStream(file));
			
			ExcelExportDocument excel = new ExcelExportDocument(fileName);
			
			List<UserAnnotationDto> mockDatas = getMockAnnotationDatas();
			DataProvider<UserAnnotationDto> dataProvider = new CommonSheetDataProvider<UserAnnotationDto>(mockDatas);
			
			// 该 sheet 页表头通过注解方式生成，具体的注解使用方式参考 UserDto.class
			excel.createSheetWithAnnotation(UserAnnotationDto.class, dataProvider);
			
			excel.doExport(os);
		} finally {
			os.close();
		}
	}
	
	@Test
	public void multiSheetExportTest() throws IOException {
		// 一个 excel 中同时导出多个 sheet 页
		OutputStream os = null;
		try {
			final String fileName = "multiSheetExport.xlsx";
			File file = new File("d:/temp/test/" + fileName);
			os = new BufferedOutputStream(new FileOutputStream(file));
			
			ExcelExportDocument excel = new ExcelExportDocument(fileName);
			
			// 创建第一个 sheet 页
			List<UserAnnotationDto> mockDatas = getMockAnnotationDatas();
			DataProvider<UserAnnotationDto> dataProvider = new CommonSheetDataProvider<UserAnnotationDto>(mockDatas);
			excel.createSheetWithAnnotation(UserAnnotationDto.class, dataProvider);
			
			// 创建第二个 sheet 页
			List<UserAnnotationDto> mockDatas2 = getMockAnnotationDatas();
			mockDatas2.add(new UserAnnotationDto("test", "361212199901013530", 50));
			DataProvider<UserAnnotationDto> dataProvider2 = new CommonSheetDataProvider<UserAnnotationDto>(mockDatas2);
			excel.createSheetWithAnnotation(UserAnnotationDto.class, dataProvider2);
			
			excel.doExport(os);
		} finally {
			os.close();
		}
	}
	
	@Test
	public void largeExcelExportTest() throws IOException {
		// -Xmx128m -Xms128m
		OutputStream os = null;
		try {
			final String fileName = "largeExcelExport.xlsx";
			File file = new File("d:/temp/test/" + fileName);
			os = new BufferedOutputStream(new FileOutputStream(file));
			
			ExcelExportDocument excel = new LargeExcelExportDocument(fileName);
			DataProvider<UserAnnotationDto> dataProvider = new LargeExportDataProvider<UserAnnotationDto>(new DataLoader<UserAnnotationDto>() {
				@Override
				public int selectTotalCount() {
					return 1000000;
				}

				// 分页加载数据方法，每次加载 pageSize 条数据
				@Override
				public Collection<UserAnnotationDto> load(int pageNum, int pageSize) {
					List<UserAnnotationDto> datas = new ArrayList<UserAnnotationDto>(pageSize);
					for(int i = 0; i < pageSize; i++) {
						UserAnnotationDto u1 = new UserAnnotationDto("zhangsan", "361212199901013530", i);
						datas.add(u1);
					}
					return datas;
				}
			}, 1000);
			
			// 该 sheet 页表头通过注解方式生成，具体的注解使用方式参考 UserDto.class
			excel.createSheetWithAnnotation(UserAnnotationDto.class, dataProvider);
			
			excel.doExport(os);
		} finally {
			os.close();
		}
	}
	
	@Test
	public void largeExcelExportWithCommonModel() throws Exception {
		// -Xmx128m -Xms128m 使用普通的 excel 导出功能导出 100W条记录，出现：java.lang.OutOfMemoryError
		OutputStream os = null;
		try {
			final String fileName = "simpleExport2.xlsx"; // 导出07版本的 excel
			File file = new File("d:/temp/test/" + fileName);
			os = new BufferedOutputStream(new FileOutputStream(file));
			
			ExcelExportDocument excel = new ExcelExportDocument(fileName);
			
			List<UserAnnotationDto> mockDatas = getLargeMockDatas();
			DataProvider<UserAnnotationDto> dataProvider = new CommonSheetDataProvider<UserAnnotationDto>(mockDatas);
			
			// 该 sheet 页表头通过注解方式生成，具体的注解使用方式参考 UserDto.class
			excel.createSheetWithAnnotation(UserAnnotationDto.class, dataProvider);
			
			excel.doExport(os);
		} finally {
			os.close();
		}
	}
	
	
	private List<UserAnnotationDto> getMockAnnotationDatas() {
		List<UserAnnotationDto> datas = new ArrayList<UserAnnotationDto>();
		UserAnnotationDto u1 = new UserAnnotationDto("zhangsan", "361212199901013530", 17);
		UserAnnotationDto u2 = new UserAnnotationDto("李四", "361212199901013530", 15);
		datas.add(u1);
		datas.add(u2);
		return datas;
	}
	
	private List<UserDto> getMockDatas() {
		List<UserDto> datas = new ArrayList<UserDto>();
		UserDto u1 = new UserDto("张三", "361212199901013530", 17);
		UserDto u2 = new UserDto("lisi", "361212199901013530", 15);
		datas.add(u1);
		datas.add(u2);
		return datas;
	}
	
	private List<UserAnnotationDto> getLargeMockDatas() {
		int totalRecord = 1000000;
		List<UserAnnotationDto> datas = new ArrayList<UserAnnotationDto>(totalRecord);
		for(int i = 0; i < totalRecord; i++) {
			UserAnnotationDto u1 = new UserAnnotationDto("zhangsan", "361212199901013530", i);
			datas.add(u1);
		}
		System.out.println("data load over ..");
		return datas;
	}

}
