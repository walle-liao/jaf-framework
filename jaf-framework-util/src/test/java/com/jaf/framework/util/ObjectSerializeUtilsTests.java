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

import java.io.File;
import java.io.NotSerializableException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import com.jaf.framework.util.io.ObjectSerializeUtils;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@gmail.com
 * @date 2012-6-12
 * @since 1.0
 */
public class ObjectSerializeUtilsTests {
	
	private static final String SERIALIZE_FILE_NAME = "target/object.ser";
	
	
	@Test(expected = NotSerializableException.class)
	public void notSerializeObject() throws Exception {
		// Object对象不可序列化，需要实现Serializable接口的对象才能被序列化
		Object o = new Object();
		ObjectSerializeUtils.serializeObject(o);
	}
	
	
	@Test
	public void canSerializeObject() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("key1", "value1");
		map.put("key2", "value2");
		
		byte[] b = ObjectSerializeUtils.serializeObject(map);
		FileUtils.writeByteArrayToFile(new File(SERIALIZE_FILE_NAME), b);
	}
	
	
	@Test
	public void canUnSerializeObject() throws Exception {
		byte[] b = FileUtils.readFileToByteArray(new File(SERIALIZE_FILE_NAME));
		Map<?, ?> map = (Map<?, ?>) ObjectSerializeUtils.unSerializeObject(b);
		Assert.assertEquals(2, map.keySet().size());
	}
}
