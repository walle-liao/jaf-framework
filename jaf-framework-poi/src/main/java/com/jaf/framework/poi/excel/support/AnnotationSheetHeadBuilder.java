package com.jaf.framework.poi.excel.support;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;

import com.jaf.framework.poi.excel.ColumnHead;
import com.jaf.framework.poi.excel.SheetHeadBuilder;
import com.jaf.framework.poi.excel.model.SheetHead;
import com.jaf.framework.poi.excel.model.SheetHeadColumn;
import com.jaf.framework.util.ReflectUtils;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年2月9日
 * @since 1.0
 */
public class AnnotationSheetHeadBuilder implements SheetHeadBuilder {

	private final Class<?> targetClazz;
	
	public AnnotationSheetHeadBuilder(Class<?> targetClazz) {
		this.targetClazz = targetClazz;
	}
	
	@Override
	public Class<?> getTargetClazz() {
		return targetClazz;
	}

	@Override
	public SheetHead build() {
		return ParseSheetHeadUtils.parseHead(targetClazz);
	}

	
	private static final class ParseSheetHeadUtils {
		
		private static final ConcurrentHashMap<Class<?>, SheetHead> sheetHeadCache = new ConcurrentHashMap<Class<?>, SheetHead>();
		
		private static SheetHead parseHead(Class<?> targetClazz) {
			SheetHead sheetHead = sheetHeadCache.get(targetClazz);
			if (sheetHead == null) {
				sheetHead = doParseHead(targetClazz);
				sheetHeadCache.put(targetClazz, sheetHead);
			}
			return sheetHead;
		}
		
		/**
		 * 根据实体类中的注解，解析出 excel 模板的表头
		 * 
		 * @param targetClazz
		 * @return
		 */
		private static SheetHead doParseHead(Class<?> targetClazz) {
			synchronized (targetClazz) {
				// retry
				SheetHead cacheHead = sheetHeadCache.get(targetClazz);
				if (cacheHead != null)
					return cacheHead;
				
				SheetHead sheetHead = new SheetHead();
				Field[] fields = ReflectUtils.getSpecified(targetClazz, ColumnHead.class);
				for (Field field : fields) {
					ColumnHead an = field.getAnnotation(ColumnHead.class);
					SheetHeadColumn heandColumn = new SheetHeadColumn(an.title(), field.getName(), an.width());
					sheetHead.appendColumn(heandColumn);
				}
				sheetHead.setFields(fields);
				return sheetHead;
			}
		}
	}
	
}
