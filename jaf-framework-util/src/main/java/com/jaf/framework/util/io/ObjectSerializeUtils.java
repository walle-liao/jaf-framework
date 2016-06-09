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
