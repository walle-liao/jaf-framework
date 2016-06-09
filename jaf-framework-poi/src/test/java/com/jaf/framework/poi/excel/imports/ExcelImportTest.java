package com.jaf.framework.poi.excel.imports;

import java.io.IOException;
import java.util.List;

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
			List<QuestionImportDTO> datas = excel.getDefaultDatas();
			System.out.println(datas.size());
			System.out.println(datas.get(5).getAnswerItemC());
			
			List<QuestionImportDTO> sheet2Datas = excel.getSheetDatas(1);
			System.out.println(sheet2Datas.size());
			System.out.println(sheet2Datas.get(5).getAnswerItemA());
		}
	}
	
}
