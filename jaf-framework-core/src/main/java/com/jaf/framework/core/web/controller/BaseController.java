package com.jaf.framework.core.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jaf.framework.core.exception.BusinessException;
import com.jaf.framework.core.exception.ParameterException;
import com.jaf.framework.core.web.controller.utils.WebUtils;
import com.jaf.framework.core.web.select.ApplicationSelectInitBean;
import com.jaf.framework.core.web.select.SelectOption;
import com.jaf.framework.util.Assert;
import com.jaf.framework.util.DateUtils;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2015年6月27日
 * @since 1.0
 */
public abstract class BaseController {
	
	protected static final String SUCCESS = "success";
	
	protected static final String MESSAGE = "msg";
	
	protected static final String ERROR_MESSAGE = "errMsg";
	
	protected static final String EXCEPTION_MESSAGE = "exceptionMsg";
	
	// 如果请求设置了noPage参数（任意值），则查询方法不使用分页
	// 对应的 getPageNum() 方法将返回 1；getPageSize() 方法将返回 Integer.MAX_VALUE
	protected static final String NO_PAGE_KEY = "noPage";
	
	protected static final String PAGE_NUMBER_REQ_KEY = "pageNumber";
	
	protected static final String PAGE_SIZE_REQ_KEY = "pageSize";
	
	
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		// 对于需要转换为Date类型的属性，使用DateEditor进行处理 
		// TODO	这里会有线程安全问题，DateConverter 中使用的 SimpleDateFormat 是线程不安全的 
	    binder.registerCustomEditor(Date.class, DateConverter.TIME_CONVERTER_INSTANCE);  
	}
	
	
	// ------------ exception handler ------------------------
	@ExceptionHandler(ParameterException.class)
	public String parameterExceptionHandler(HttpServletRequest request, Exception exception) {
		request.setAttribute(EXCEPTION_MESSAGE, exception.getMessage());
		return "error-parameter";
	}
	
	@ExceptionHandler(BusinessException.class)
	public String businessExceptionHandler(HttpServletRequest request, Exception exception) {
		request.setAttribute(EXCEPTION_MESSAGE, exception.getMessage());
		return "error-business";
	}
	
	
	// --------- parameter about --------
	protected int getPageNum() {
		if (isNoPage())
			return 1;
			
		String pageNumStr = getRequest().getParameter(PAGE_NUMBER_REQ_KEY);
		if (StringUtils.isNoneBlank(pageNumStr))
			return Integer.valueOf(pageNumStr);
		return 1;
	}
	
	
	protected int getPageSize() {
		if (isNoPage())
			return Integer.MAX_VALUE;
			
		String pageSize = getRequest().getParameter(PAGE_SIZE_REQ_KEY);
		if (StringUtils.isNoneBlank(pageSize))
			return Integer.valueOf(pageSize);
		return 0;
	}
	
	
	protected boolean isNoPage() {
		String noPage = getRequest().getParameter(NO_PAGE_KEY);
		return StringUtils.isNoneBlank(noPage);
	}
	
	
	protected Map<String, Object> getQueryParam() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		
		HttpServletRequest request = getRequest();
		Map<?, ?> map = request.getParameterMap();
		for (Map.Entry<?, ?> entry : map.entrySet()) {
			Object key = entry.getKey();
			String value = request.getParameter(key.toString());
			if (StringUtils.isNoneBlank(value)) {
				parameters.put(key.toString(), value);
			}
		}
		return parameters;
	}
	
	
	protected String getString(String name, String defaultValue) {
		String value = getRequest().getParameter(name);
		if (StringUtils.isBlank(value) || "null".equals(value) || "undefined".equals(value)) {
			return defaultValue;
		}
		return value;
	}
	
	
	protected String getString(String name) {
		return getString(name, null);
	}
	
	
	protected String getStringSafe(String name) {
		return getString(name, "");
	}
	
	
	protected Date getDate(String name, Date defaultValue, String pattern) {
		Assert.hasText(name, "The name must not be null or empty");
		Assert.hasText(pattern, "The pattern must not be null or empty");
		
		String dateValue = getString(name);
		if (StringUtils.isNoneBlank(dateValue)) {
			return DateUtils.parseSafe(dateValue, pattern);
		}
		return defaultValue;
	}
	
	
	protected Date getDate(String name, String pattern) {
		return getDate(name, null, pattern);
	}
	
	
	protected Date getDate(String name) {
		return getDate(name, null, DateUtils.YYYY_MM_DD);
	}
	
	
	protected List<SelectOption> getOptions(Class<? extends Enum<?>> clazzEnum) {
		List<SelectOption> options = null;
		
		// 1.先从系统初始化缓存中获取
		ApplicationSelectInitBean applicationSelect = getBeanByType(
				ApplicationSelectInitBean.class);
		if (applicationSelect != null) {
			options = applicationSelect.getSelect(clazzEnum);
		}
		
		// 2.如果系统没有初始化缓存，则手动获取
		if (options == null && clazzEnum.isEnum()) {
			Enum<?>[] values = (Enum<?>[]) clazzEnum.getEnumConstants();
			options = new ArrayList<SelectOption>(values.length);
			for (Enum<?> e : values) {
				SelectOption option = new SelectOption(e.name(), e.name());
				options.add(option);
			}
		}
		return options;
	}
	
	
	// --------- response --------------
	protected Map<String, Object> successResponse(String msg) {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(SUCCESS, Boolean.TRUE);
		response.put(MESSAGE, msg);
		return response;
	}
	
	protected Map<String, Object> successResponse() {
		return successResponse("操作成功");
	}

	
	protected Map<String, Object> failResponse(String errMsg) {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(SUCCESS, Boolean.FALSE);
		response.put(ERROR_MESSAGE, errMsg);
		return response;
	}
	
	
	// ----- util methods ------
	protected HttpServletRequest getRequest() {
		return WebUtils.getRequest();
	}
	
	
	protected HttpServletResponse getResponse() {
		return WebUtils.getResponse();
	}
	
	
	protected HttpSession getSession() {
		return WebUtils.getSession();
	}
	
	
	protected ServletContext getServletContext() {
		return WebUtils.getServletContext();
	}
	
	
	protected String getContextPath() {
		return WebUtils.getContextPath();
	}
	
	
	protected ApplicationContext getApplicationContext() {
		return WebApplicationContextUtils.getWebApplicationContext(getServletContext());
	}
	
	
	protected Object getBean(String beanName) {
		return getApplicationContext().getBean(beanName);
	}
	
	
	protected <T> T getBeanByType(Class<T> clazz) {
		return getApplicationContext().getBean(clazz);
	}
	
	
	protected void addCookie(String key, String value, String domain, String path, int expiry) {
		WebUtils.setCookie(key, value, domain, path, expiry);
	}
	
	
	protected void addCookie(String key, String value, String domain, int expiry) {
		WebUtils.setCookie(key, value, domain, expiry);
	}
	
	
	protected void addCookie(String key, String value, int expiry) {
		WebUtils.setCookie(key, value, expiry);
	}
	
	
	protected String getCookie(String key) {
		return WebUtils.getCookieValue(getRequest(), key);
	}
	
	
	protected void clearCookie(String key) {
		WebUtils.clearCookie(key);
	}
	
	
	protected String getIpAddr() {
		HttpServletRequest request = getRequest();
		String ip = request.getHeader("x-forwarded-for");
		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	
	// ------------------- file upload ------------------------

	protected enum PageType {
		ADD, EDIT, VIEW, DELETE, LIST;
	}

}


