package com.jaf.framework.core.web.controller.utils;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.jaf.framework.util.io.IoUtils;

/**
 * 
 * ajax返回工具类
 * 
 * @author liaozhicheng
 * @date 2011-9-28
 * @since 1.0
 */
public class AjaxUtils {
	
	public static void jsonReturn(HttpServletResponse response, String jsonStr) {
		if(response == null)
			response = WebUtils.getResponse();
		
		response.setContentType("text/xml; charset=UTF-8");
		
		Writer writer = null;
		try {
			writer = response.getWriter();
			writer.write(jsonStr);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IoUtils.closeSilently(writer);
		}
	}
	
	
	public static void jsonReturn(HttpServletResponse response, Map<String, Object> result) {
		jsonReturn(response, JSONUtils.parse(result));
	}
	
	
	private AjaxUtils() {}
	
}
