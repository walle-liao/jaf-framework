package com.jaf.framework.poi.excel.export;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.jaf.framework.poi.excel.DefaultColumnValueHandler;
import com.jaf.framework.poi.excel.SheetDataProvider;
import com.jaf.framework.poi.excel.export.dto.UserExportDTO;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年2月11日
 * @since 1.0
 */
public class ExcelExportTest {
	
	@Test
	public void testExport() throws Exception {
		File file = new File("d:/test.xls");
		OutputStream os = new BufferedOutputStream(new FileOutputStream(file));
		
		ExcelExportDocument excel = new ExcelExportDocument("test.xls");
		List<UserExportDTO> datas = new ArrayList<UserExportDTO>();
		UserExportDTO u1 = new UserExportDTO("展示", "252240", 1);
		UserExportDTO u2 = new UserExportDTO("历史", "005511", 15);
		datas.add(u1);
		datas.add(u2);
		SheetDataProvider dataProvider = new SheetDataProvider(datas);
		dataProvider.registValueHandler("", new DefaultColumnValueHandler() {
			@Override
			public String processExportValue(Object value) {
				return "";
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

}
