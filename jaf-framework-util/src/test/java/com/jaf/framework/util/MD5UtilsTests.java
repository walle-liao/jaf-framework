/*
 * Copyright 2012-2014 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
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
