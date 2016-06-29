package com.jaf.framework.poi.excel.imports;

import java.io.IOException;
import java.util.Collection;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import com.jaf.framework.poi.excel.imports.dto.QuestionImportDTO;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年2月10日
 * @since 1.0
 */
public class ExcelImportTest {

	@Test
	public void testImport() throws IOException {
		ClassPathResource resource = new ClassPathResource("testImport.xls");
		ExcelImportDocument excel = new ExcelImportDocument(resource.getFile());
		excel.createSheetWithAnnotation(QuestionImportDTO.class);
		excel.createSheetWithAnnotation(QuestionImportDTO.class);
		if(excel.doImport()) {
			Collection<QuestionImportDTO> datas = excel.getDefaultDatas(QuestionImportDTO.class);
			System.out.println(datas.size());
			
			Collection<QuestionImportDTO> sheet2Datas = excel.getSheetDatas(1, QuestionImportDTO.class);
			System.out.println(sheet2Datas.size());
		}
	}
	
}
