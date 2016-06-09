package com.jaf.framework.core.web.controller.utils;

import java.util.Collection;
import java.util.Date;

import com.jaf.framework.util.DateUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

/**
 * 
 * @author liaozhicheng
 * @date 2011-8-1
 * @Since 1.0
 */
public class JSONUtils {
	
	private static final JsonConfig jsonConfig = new JsonConfig();
	
	static {
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor());
	}

	/**
	 * 注：如果obj是一个非public类或者含有非public类或者内部类的属性情况下会出现异常
	 */
	public static String parse(Object object) {
		String jsonString = null;
		if (object != null) {
			if (object instanceof Collection || object instanceof Object[]) {
				jsonString = JSONArray.fromObject(object, jsonConfig).toString();
			} else {
				jsonString = JSONObject.fromObject(object, jsonConfig).toString();
			}
		}
		return jsonString == null ? "{}" : jsonString;		
	}
	
	private JSONUtils() {
	}
	
}

class JsonDateValueProcessor implements JsonValueProcessor {

	public JsonDateValueProcessor() {
	}

	@Override
	public Object processArrayValue(Object value, JsonConfig jsonConfig) {
		return process(value, jsonConfig);
	}

	@Override
	public Object processObjectValue(String key, Object value,
			JsonConfig jsonConfig) {
		return process(value, jsonConfig);
	}

	private Object process(Object value, JsonConfig jsonConfig) {
		if (value instanceof Date) {
			// thread safe
			return DateUtils.formatYMD((Date) value);
		}
		return value == null ? null : value.toString();
	}

}