package com.jaf.framework.core.web.select;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.ClassUtils;

/**
 * 包扫描bean，支持多个包扫描和子包递归扫描
 * 
 * @author liaozhicheng
 * @date 2015-1-26
 * @since 1.0
 */
public class ApplicationPackageScanBean implements InitializingBean {
	
	private static final String RESOURCE_PATTERN = "/**/*.class";
	
	private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
	
	// 指定哪些包需要被扫描，支持多个包"foo.a,foo.b"并对每个包都会递归搜索
	private String[] packagesToScan;
	
	// 指定扫描包中含有特定注解标记的类，支持多个注解
	private Class<? extends Annotation>[] annotationFilterClazz;
	
	private final List<TypeFilter> typeFilters = new ArrayList<TypeFilter>();
	
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if (annotationFilterClazz != null && annotationFilterClazz.length > 0) {
			for (Class<? extends Annotation> clazz : annotationFilterClazz) {
				typeFilters.add(new AnnotationTypeFilter(clazz));
			}
		}
	}
	
	
	/**
	 * 扫描 package 获取所有符合过滤条件的 Class
	 * 
	 * @see AnnotationSessionFactoryBean#scanPackages 参考hibernate里面的代码
	 * @return
	 */
	public Set<Class<?>> scanPackages() {
		Set<Class<?>> matchClazz = new HashSet<Class<?>>();
		
		try {
			if (this.packagesToScan != null && this.packagesToScan.length > 0) {
				for (String pkg : packagesToScan) {
					String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
							+ ClassUtils.convertClassNameToResourcePath(pkg) + RESOURCE_PATTERN;
					Resource[] resources = resourcePatternResolver.getResources(pattern);
					MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(
							resourcePatternResolver);
					for (Resource resource : resources) {
						if (resource.isReadable()) {
							MetadataReader reader = readerFactory.getMetadataReader(resource);
							String className = reader.getClassMetadata().getClassName();
							if (matchesEntityTypeFilter(reader, readerFactory)) {
								matchClazz.add(resourcePatternResolver.getClassLoader().loadClass(
										className));
							}
						}
					}
				}
			}
		}
		catch (IOException ex) {
			throw new IllegalArgumentException("Failed to scan classpath for unlisted classes", ex);
		}
		catch (ClassNotFoundException ex) {
			throw new IllegalArgumentException("Failed to load annotated classes from classpath",
					ex);
		}
		
		return matchClazz;
	}
	
	
	/**
	 * 检查当前扫描到的Bean含有任何一个指定的注解标记
	 * 
	 * @param reader
	 * @param readerFactory
	 * @return
	 * @throws IOException
	 */
	private boolean matchesEntityTypeFilter(MetadataReader reader,
			MetadataReaderFactory readerFactory) throws IOException {
		if (!this.typeFilters.isEmpty()) {
			for (TypeFilter filter : this.typeFilters) {
				if (filter.match(reader, readerFactory)) {
					return true;
				}
			}
		}
		return false;
	}
	
	
	public void setPackagesToScan(String[] packagesToScan) {
		this.packagesToScan = packagesToScan;
	}
	
	
	public void setAnnotationFilterClazz(Class<? extends Annotation>[] annotationFilterClazz) {
		this.annotationFilterClazz = annotationFilterClazz;
	}
	
}
