package com.jaf.framework.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 * 对字符串进行MD5加密处理
 * 
 * @author liaozhicheng
 * @date 2012-6-11
 * @since 1.0
 */
public class MD5Utils {
	
	private MD5Utils() {
		
	}
	
	
	/**
	 * 对字符串进行MD5加密处理
	 * <p>
	 * 如果传入的参数为空字符串或者<code>null</code>，将返回一个<code>IllegalArgumentException</code>
	 * </p>
	 * 
	 * @param source
	 * @return
	 */
	public static String md5Encode(String source) {
		Assert.hasLength(source);
		
		StringBuilder code = new StringBuilder();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(source.getBytes());
			byte b[] = md.digest();
			int i;
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					code.append("0");
				code.append(Integer.toHexString(i));
			}
		}
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return code.toString();
	}
	
}
