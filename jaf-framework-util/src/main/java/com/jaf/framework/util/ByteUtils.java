/*
 * Copyright 2012-2017 the original author or authors.
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

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2015年8月23日
 * @since 1.0
 */
public final class ByteUtils {
	
	public static String toBit(int arg) {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < 32; i++) {
			int t = (arg & 0x80000000 >>> i) >>> (31 - i);
			result.append(t);
		}
		return result.toString();
	}
	
	
	public static void main(String[] args) {
		System.out.println(toBit(Integer.MIN_VALUE));
	}
	
	
	private ByteUtils() {
		
	}
}
