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
