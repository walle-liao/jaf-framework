package com.jaf.framework.util.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.jaf.framework.util.Assert;

/**
 * 对象序列化和反序列化工具类
 * 
 * @author liaozhicheng.cn@gmail.com
 * @date 2012-6-12
 * @since 1.0
 */
public class ObjectSerializeUtils {
	
	private ObjectSerializeUtils() {
		// no instances
	}
	
	
	/**
	 * 将一个对象序列化成一个字节数组
	 * <p>
	 * 如果传入的对象为<code>null</code>将抛出<code>IllegalArgumentException</code>异常
	 * </p>
	 * 
	 * @param obj
	 * @return
	 * @throws IOException
	 */
	public static byte[] serializeObject(Object obj) throws IOException {
		
		Assert.notNull(obj, "Parameter obj can not be null");
		
		byte[] result = null;
		
		ByteArrayOutputStream byteOut = null;
		ObjectOutputStream objOut = null;
		try {
			byteOut = new ByteArrayOutputStream();
			objOut = new ObjectOutputStream(byteOut);
			objOut.writeObject(obj);
			
			result = byteOut.toByteArray();
		}
		finally {
			IoUtils.closeSilently(byteOut);
			IoUtils.closeSilently(objOut);
		}
		
		return result;
	}
	
	
	/**
	 * 将一个byte数组反序列化成一个对象
	 * 
	 * @param bytes
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Object unSerializeObject(byte[] bytes) throws IOException, ClassNotFoundException {
		
		Assert.isTrue(bytes != null && bytes.length > 0);
		
		Object result = null;
		
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
			result = ois.readObject();
		}
		finally {
			IoUtils.closeSilently(ois);
		}
		
		return result;
	}
	
}
