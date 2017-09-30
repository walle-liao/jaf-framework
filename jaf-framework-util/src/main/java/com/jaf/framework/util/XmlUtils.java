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
