package com.jaf.framework.util;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@gmail.com
 * @date 2012-6-11
 * @since 1.0
 */
public class MD5UtilsTests {
	
	@Test
	public void canEncodeNormalString() {
		String result = MD5Utils.md5Encode("000000");
		Assert.assertEquals("670b14728ad9902aecba32e22fa4f6bd", result);
	}
	
	
	@Test
	public void canEncodeWhitespaceString() {
		String result = MD5Utils.md5Encode(" ");
		Assert.assertEquals("7215ee9c7d9dc229d2921a40e899ec5f", result);
	}
	
	
	@Test
	public void canEncodeSpecialCharacter() {
		String result = MD5Utils.md5Encode("$1%中文,eng，_】");
		Assert.assertEquals("d1b70f5da98db35bf8bd536d6736b4a0", result);
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void encodeNullThrowException() {
		MD5Utils.md5Encode(null);
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void encodeEmptyStringThrowException() {
		MD5Utils.md5Encode(StringUtils.EMPTY);
	}
	
}
