package com.jaf.framework.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 业务异常
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2015年10月1日
 * @since 1.0
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class BusinessException extends Exception {

	private static final long serialVersionUID = 8806526873377289825L;
	
	
	public BusinessException() {
		super();
	}
	
	
	public BusinessException(String s) {
        super(s);
    }
	
	
	public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
	
	
	public BusinessException(Throwable cause) {
		super(cause);
	}
	
}
