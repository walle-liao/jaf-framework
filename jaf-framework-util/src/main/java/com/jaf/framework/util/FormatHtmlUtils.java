package com.jaf.framework.util;

import org.apache.commons.lang3.StringUtils;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2011-12-20
 * @since 1.0
 */
public class FormatHtmlUtils {
	
	public static String format(String tag) {
		
		return formatHtmlLineSeparator(formatHtmlRemark(formatApostrophe(tag)));
	}
	
	
	/**
	 * 格式化html注释 对于如下html注释：<!--[if !supportLists]--> 在chrome下会被当成注释，而在ie下会当成字符串显示出来 需要修改成：<!-- [if
	 * !supportLists] --> ie才会把这串当成注释
	 * 
	 * @param tag
	 * @return
	 */
	public static String formatHtmlRemark(String tag) {
		if (StringUtils.isEmpty(tag)) {
			return tag;
		}
		
		tag = tag.replaceAll("<!--", "<!-- ").replaceAll("-->", " -->");
		return tag;
	}
	
	
	/**
	 * 格式化html的换行符
	 * 
	 * @param tag
	 * @return
	 */
	public static String formatHtmlLineSeparator(String tag) {
		if (StringUtils.isEmpty(tag)) {
			return tag;
		}
		
		tag = tag.replaceAll("\r", "\\\\r").replaceAll("\n", "\\\\n");
		return tag;
	}
	
	
	/**
	 * &quot; 属于html特殊符号编码
	 * 
	 * @param tag
	 * @return
	 */
	public static String formatDoubleQuotation(String tag) {
		if (StringUtils.isEmpty(tag)) {
			return tag;
		}
		
		tag = tag.replaceAll("\"", "&quot;");
		return tag;
	}
	
	
	/**
	 * 将英文的单引号修改成引文的双引号
	 * 
	 * @param tag
	 * @return
	 */
	public static String formatApostrophe(String tag) {
		if (StringUtils.isEmpty(tag)) {
			return tag;
		}
		
		tag = tag.replaceAll("\'", "\\\\'");
		return tag;
	}
	
}
