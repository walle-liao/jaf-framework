package com.jaf.framework.poi.excel.export;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.jaf.framework.poi.excel.DataProvider;
import com.jaf.framework.poi.excel.dto.UserAnnotationDto;
import com.jaf.framework.poi.excel.dto.UserDto;
import com.jaf.framework.poi.excel.export.dto.UserExportDTO;
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
	public void testExport() throws Exception {
		final String fileName = "test.xls";
		File file = new File("d:/temp/" + fileName);
		OutputStream os = new BufferedOutputStream(new FileOutputStream(file));
		
		ExcelExportDocument excel = new ExcelExportDocument(fileName);
		List<UserExportDTO> datas = new ArrayList<UserExportDTO>();
		UserExportDTO u1 = new UserExportDTO("展示", "252240", 1);
		UserExportDTO u2 = new UserExportDTO("历史", "005511", 15);
		datas.add(u1);
		datas.add(u2);
		CommonSheetDataProvider<UserExportDTO> dataProvider = new CommonSheetDataProvider<UserExportDTO>(datas);
		dataProvider.registValueHandler("name", new DefaultColumnValueHandler() {
			@Override
			public String processExportValue(Object value) {
				return ((UserExportDTO) value).getName();
			}
		});
		excel.createSheetWithAnnotation(UserExportDTO.class, dataProvider);
		
		List<UserExportDTO> data2s = new ArrayList<UserExportDTO>();
		UserExportDTO u21 = new UserExportDTO("展示22", "252240", 10);
		UserExportDTO u22 = new UserExportDTO("历史22", "005511", 150);
		data2s.add(u21);
		data2s.add(u22);
		data2s.add(u1);
		excel.createSheetWithAnnotation(UserExportDTO.class, data2s);
		
		excel.doExport(os);
		
		os.close();
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

}
