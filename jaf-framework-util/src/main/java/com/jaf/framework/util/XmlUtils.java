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

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

import org.apache.commons.beanutils.BeanUtils;
import org.dom4j.Attribute;
import org.dom4j.Element;

/**
 * 
 * TODO
 * 
 * @author liaozhicheng
 * @date 2012-6-12
 * @since 1.0
 */
public class XmlUtils {
	
	private XmlUtils() {
	}
	
	
	public static void copyElementAttribute(Element xmlElement, Object target)
			throws IllegalAccessException, InvocationTargetException {
		
		Assert.notNull(xmlElement);
		Assert.notNull(target);
		
		Iterator<?> attributeIterator = xmlElement.attributeIterator();
		while (attributeIterator.hasNext()) {
			Attribute attribute = (Attribute) attributeIterator.next();
			String name = attribute.getName();
			String value = attribute.getValue();
			BeanUtils.copyProperty(target, name, value);
		}
	}
	
}
