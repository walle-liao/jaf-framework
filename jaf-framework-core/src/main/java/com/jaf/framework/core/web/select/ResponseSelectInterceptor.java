package com.jaf.framework.core.web.select;

import java.lang.reflect.Method;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.jaf.framework.core.web.select.annotation.NameSelectEunm;
import com.jaf.framework.core.web.select.annotation.ResponseSelect;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2015-1-26
 * @since 1.0
 */
public class ResponseSelectInterceptor extends HandlerInterceptorAdapter {

	@Resource(name = "applicationSelectInitBean")
	private ApplicationSelectInitBean applicationSelectInitBean;
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
			
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();
			if (method.isAnnotationPresent(ResponseSelect.class)) {
				ResponseSelect annotation = method.getAnnotation(ResponseSelect.class);
				setResponseSelectInternal(annotation, request);
			}
		} else if (handler instanceof Controller) {
			if (handler.getClass().isAnnotationPresent(ResponseSelect.class)) {
				ResponseSelect annotation = handler.getClass().getAnnotation(ResponseSelect.class);
				setResponseSelectInternal(annotation, request);
			}
		}
		return true;
	}
	
	
	private void setResponseSelectInternal(ResponseSelect annotation, HttpServletRequest request) {
		Class<? extends Enum<?>>[] enumClazzs = annotation.selectEnums();
		for (Class<? extends Enum<?>> clazz : enumClazzs) {
			request.setAttribute(getSimpleNameKey(clazz), applicationSelectInitBean.getSelect(clazz));
		}
		
		NameSelectEunm[] nameSelectEnums = annotation.nameSelectEnums();
		for (NameSelectEunm nameSelectEnum : nameSelectEnums) {
			String key = nameSelectEnum.key();
			request.setAttribute(key, applicationSelectInitBean.getSelect(nameSelectEnum.selectEnum()));
		}
	}
	
	
	private String getSimpleNameKey(Class<?> clazz) {
		String classSimpleName = clazz.getSimpleName();
		return classSimpleName.substring(0, 1).toLowerCase() + classSimpleName.substring(1);
	}
	
	
}
