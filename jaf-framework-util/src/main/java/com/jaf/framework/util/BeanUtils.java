package com.jaf.framework.util;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.beanutils.converters.IntegerConverter;

/**
 * 
 * 扩展apache的BeanUtils类，实现copyProperties时能够按指定过滤条件不需要copy的属性,通过在<code>filters</code>
 * 中指定，另对于基本数据类型int的封装类型Integer拷贝的时候，如果属性的类型为Integer，并且该属性的值为null，则拷贝之后的对象中该属性的值仍为null，
 * 而apache的BeanUtils类拷贝之后该属性的值为0，除int之外其他基本类型的封装类型没有做处理
 * 
 * @author liaozhicheng
 * @date 2012-6-11
 * @since 1.0
 */
public class BeanUtils {
	
	public static void copyProperties(Object dest, Object orig) {
		copyProperties(dest, orig, new String[0]);
	}
	
	
	public static void copyProperties(Object dest, Object orig, final String[] filters) {
		
		// 默认ConvertUtilsBean中会将Integer类型的null转换成0，这里不需要转换成0
		// 所以需要先取消已注册的Integer类型的转换器，然后手动注册,详见代码 ConvertUtilsBean#registerStandard()
		ConvertUtilsBean converUtilsBean = BeanUtilsBean.getInstance().getConvertUtils();
		converUtilsBean.deregister(Integer.class);
		converUtilsBean.register(new IntegerConverter(null), Integer.class);
		
		BeanUtilsBean beanUtils = new BeanUtilsBean(converUtilsBean, new PropertyUtilsBean() {
			
			// 通过覆盖父类PropertyUtilsBean#getPropertyDescriptors方法实现指定属性不copy，默认会copy所有属性名相同的属性
			
			@Override
			public PropertyDescriptor[] getPropertyDescriptors(Object bean) {
				PropertyDescriptor[] descriptors = super.getPropertyDescriptors(bean);
				
				if (descriptors == null || descriptors.length == 0 || filters == null
						|| filters.length == 0)
					return descriptors;
				
				List<PropertyDescriptor> descriptorList = new ArrayList<PropertyDescriptor>(
						Arrays.asList(descriptors));
				List<String> filterList = Arrays.asList(filters);
				
				// 过滤掉不需要copy值的属性
				Iterator<PropertyDescriptor> descriptorIterator = descriptorList.iterator();
				while (descriptorIterator.hasNext()) {
					PropertyDescriptor descriptor = descriptorIterator.next();
					if (filterList.contains(descriptor.getName())) {
						descriptorIterator.remove();
					}
				}
				
				return descriptorList.toArray(new PropertyDescriptor[descriptorList.size()]);
			}
			
		});
		try {
			beanUtils.copyProperties(dest, orig);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
}
