package com.jaf.framework.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 做远程接口联调时，方便回放远程调用
 * 
 * @author liaozhicheng
 * @date 2015年11月20日
 * @since 1.0
 */
public class ObjectByteUtils {

	/**
	 * 将对象转换成字节数组
	 * @param object
	 * @return
	 */
	public static byte[] toByteArray(Object object) {
		if (object == null)
			return null;

		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(byteOut);
			out.writeObject(object);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(out);
			IOUtils.closeQuietly(byteOut);
		}
		return byteOut.toByteArray();
	}

	/**
	 * 将字节数组转换成实体
	 * @param objectBytes
	 * @return
	 */
	public static Object parseObject(byte[] objectBytes) {
		if (objectBytes == null || objectBytes.length == 0)
			return null;

		ByteArrayInputStream byteIn = new ByteArrayInputStream(objectBytes);
		ObjectInputStream in = null;
		Object obj = null;
		try {
			in = new ObjectInputStream(byteIn);
			obj = in.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(byteIn);
		}
		return obj;
	}

	/**
	 * 对象转换成16进账字符串，先将对象转成字节数组，然后将字节数组转换成16进账字符串
	 * @param object
	 * @return
	 */
	public static String toHexStr(Object object) {
		byte[] objectBytes = toByteArray(object);
		if (objectBytes != null) {
			StringBuilder sb = new StringBuilder(objectBytes.length);
			String sTemp;
			for (int i = 0; i < objectBytes.length; i++) {
				sTemp = Integer.toHexString(0xFF & objectBytes[i]);
				if (sTemp.length() < 2)
					sb.append(0);
				sb.append(sTemp.toUpperCase());
			}
			return sb.toString();
		}
		return "";
	}

	/**
	 * 将16进制字符串转换成对象
	 * @param hexStr
	 * @return
	 */
	public static Object hexStrToObj(String hexStr) {
		if (StringUtils.isBlank(hexStr))
			return null;

		int len = hexStr.length() / 2;
		byte[] objByte = new byte[len];
		char[] achar = hexStr.toCharArray();
		for (int i = 0; i < len; i++) {
			int pos = i * 2;
			objByte[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
		}
		return parseObject(objByte);
	}

	private static int toByte(char c) {
		byte b = (byte) "0123456789ABCDEF".indexOf(c);
		return b;
	}
	
	
	static class ObjectFileWrapper {
		
		
		
	}
	
}
