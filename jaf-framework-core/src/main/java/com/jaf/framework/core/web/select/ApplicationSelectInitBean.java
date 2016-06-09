package com.jaf.framework.core.web.select;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

import com.jaf.framework.core.web.select.annotation.OptionText;
import com.jaf.framework.core.web.select.annotation.OptionValue;

/**
 * 初始化并缓系统中所有枚举到下拉框转换关系 所有的枚举到下拉框的对应关系，只会在系统启动的时候做一次转换，然后缓存，不会每次调用的时候都转换
 * 
 * @author liaozhicheng
 * @date 2015-1-26
 * @since 1.0
 */
public class ApplicationSelectInitBean implements InitializingBean {
	
	private static final Logger LOG = Logger.getLogger(ApplicationSelectInitBean.class);
	
	// 包的扫描工具类
	private ApplicationPackageScanBean scaner;
	
	// 用以缓存系统中所有的从枚举转换成下拉框对象
	private static final Map<Class<?>, List<SelectOption>> SYSTEM_SELECT_CACHE = new HashMap<Class<?>, List<SelectOption>>();
	
	private static final String ENUM_DEFAULT_TEXT_KEY = "text";
	
	private static final String ENUM_DEFAULT_VALUE_KEY = "value";
	
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Set<Class<?>> selectEnumClazz = scaner.scanPackages();
		try {
			if (selectEnumClazz.isEmpty())
				return;
			
			for (Class<?> clazz : selectEnumClazz) {
				if (clazz.isEnum()) {
					Enum<?>[] values = (Enum<?>[]) clazz.getEnumConstants();
					List<SelectOption> options = new ArrayList<SelectOption>(values.length);
					
					for (Enum<?> objEnum : values) {
						SelectOption option = new SelectOption(null, null);
						// 反射获取枚举类的属性，转换成 SelectOption 对象
						Field[] fields = objEnum.getClass().getDeclaredFields();
						
						// 下面这三个值填充步骤可以抽取出接口，后续如果更复杂之后可以优化
						// 第一次填充值，先根据枚举中配置的注解来填充值（优先级最高，如果有配置注解，则后面两步被忽略）
						fillValueByAnnotation(option, objEnum, fields);
						
						// 第二次值的填充，如果没有配置相应的注解，根据枚举的属性名来填充（优先级其次，如果没有注解，则根据属性名来获取值）
						fillValueByPropertyName(option, objEnum, fields);
						
						// 第三次填充，直接根据枚举的值来填充（优先级最低，如果前两步都无法获取到值才被使用）
						fillValueByDefault(option, objEnum);
						
						options.add(option);
					}
					
					// 缓存该下拉框
					SYSTEM_SELECT_CACHE.put(clazz, options);
				}
			}
			
			if(LOG.isDebugEnabled()) {
				LOG.debug("=============== select option scan =================");
				for(Map.Entry<Class<?>, List<SelectOption>> entry : SYSTEM_SELECT_CACHE.entrySet()) {
					StringBuilder sb = new StringBuilder();
					sb.append(entry.getKey().getName()).append(" : ").append(Arrays.toString(entry.getValue().toArray()));
					LOG.debug(sb.toString());
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private void fillValueByAnnotation(SelectOption option, Enum<?> objEnum, Field[] fields)
			throws Exception {
		for (Field field : fields) {
			field.setAccessible(true);
			if (field.isAnnotationPresent(OptionText.class)) {
				option.setText(field.get(objEnum));
				continue;
			}
			if (field.isAnnotationPresent(OptionValue.class)) {
				option.setValue(field.get(objEnum));
			}
		}
	}
	
	
	private void fillValueByPropertyName(SelectOption option, Enum<?> objEnum, Field[] fields)
			throws Exception {
		if (option.isFull())
			return;
		
		for (Field field : fields) {
			field.setAccessible(true);
			if (ENUM_DEFAULT_TEXT_KEY.equals(field.getName()) && option.getText() == null) {
				option.setText(field.get(objEnum));
				continue;
			}
			if (ENUM_DEFAULT_VALUE_KEY.equals(field.getName()) && option.getValue() == null) {
				option.setValue(field.get(objEnum));
			}
		}
	}
	
	
	private void fillValueByDefault(SelectOption option, Enum<?> objEnum) {
		if (option.isFull())
			return;
		
		if (option.getText() == null) {
			option.setText(objEnum.name());
		}
		if (option.getValue() == null) {
			option.setValue(objEnum.name());
		}
	}
	
	
	public List<SelectOption> getSelect(Class<? extends Enum<?>> clazzEnum) {
		if (SYSTEM_SELECT_CACHE.containsKey(clazzEnum)) {
			return SYSTEM_SELECT_CACHE.get(clazzEnum);
		}
		return new ArrayList<SelectOption>();
	}
	
	
	public void setScaner(ApplicationPackageScanBean scaner) {
		this.scaner = scaner;
	}
	
}
