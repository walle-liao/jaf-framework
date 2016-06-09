package com.jaf.framework.core.web.controller.utils;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


/**
 * 扩展 <code>org.springframework.web.util.WebUtils</code>
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2015年6月28日
 * @since 1.0
 */
public abstract class WebUtils extends org.springframework.web.util.WebUtils {
	
	
	public static HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}
	
	
	public static HttpServletResponse getResponse() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
	}
	
	
	public static HttpSession getSession() {
		return getRequest().getSession();
	}
	
	
	public static ServletContext getServletContext() {
		return ContextLoader.getCurrentWebApplicationContext().getServletContext();
	}
	
	
	public static String getContextPath() {
		return getRequest().getContextPath();
	}
	
	
	public static String getCookieValue(HttpServletRequest request, String key) {
		Cookie cookie = getCookie(request, key);
		if(cookie != null) {
			return cookie.getValue();
		}
		return "";
	}
	
	
	public static void setCookie(String name, String value, String domain, String path, int expiry) {
		Cookie cookie = new Cookie(name, value);
		if (StringUtils.isNoneEmpty(domain))
			cookie.setDomain(domain);
		cookie.setMaxAge(expiry);
		cookie.setPath(path);
		getResponse().addCookie(cookie);
	}
	
	public static void setCookie(String name, String value, String domain, int expiry) {
		setCookie(name, value, domain, getRequest().getContextPath(), expiry);
	}
	
	public static void setCookie(String name, String value, int expiry) {
		setCookie(name, value, "", getRequest().getContextPath(), expiry);
	}
	
	public static void setCookie(String name, String value) {
		setCookie(name, value, -1);
	}

	
	public static void clearCookie(String name) {
		if(StringUtils.isBlank(name))
			return;
		
		Cookie[] cookies = getRequest().getCookies();
		if(cookies != null) {
			for(Cookie cookie : cookies) {
				if(name.equals(cookie.getName())) {
					cookie.setValue(null);
					cookie.setMaxAge(0);
					cookie.setDomain("");
					cookie.setPath(getRequest().getContextPath());
					getResponse().addCookie(cookie);
				}
			}
		}
	}
	
	
	public static String getSessionIdWithCookie(HttpServletRequest request, String cookieKey) {
		String sessionId = request.getSession().getId();
		Cookie cookie = getCookie(request, cookieKey);
		if(cookie != null) {
			sessionId = cookie.getValue();
		}
		return sessionId;
	}
	
}
