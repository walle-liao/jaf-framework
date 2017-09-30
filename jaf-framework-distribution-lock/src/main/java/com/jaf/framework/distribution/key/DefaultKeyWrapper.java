package com.jaf.framework.distribution.key;

import com.jaf.framework.distribution.KeyWrapper;

/**
 * 不做任何处理
 * 
 * @author liaozhicheng.cn@163.com
 * @since 1.0
 */
public class DefaultKeyWrapper implements KeyWrapper {

	@Override
	public String wrap(String key) {
		return key;
	}

}
