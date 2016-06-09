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

import java.text.Format;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.FastDateFormat;

/**
 * 日期工具类
 * 
 * @author liaozhicheng
 * @date 2015年5月23日
 * @since 1.0
 */
public final class DateUtils extends org.apache.commons.lang3.time.DateUtils {
	
	private DateUtils() {
		// no instances
	}
	
	private static final int WEEKS = 7;
	
	// ------ pattern ------------
	
	public static final String YEAR = "yyyy";
	
	public static final String MONTH = "MM";
	
	public static final String DAY = "dd";
	
	// 24小时制 (0~23)
	public static final String HOUR_24 = "HH";
	
	// 12小时制 (1~12)
	public static final String HOUR_12 = "hh";
	
	public static final String MINUTE = "mm";
	
	public static final String SECOND = "ss";
	
	public static final String MILLISECOND = "SSS";
	
	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	
	public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
	
	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	
	public static final String YYYY_MM_DD_HH_MM_SS_MS = "yyyy-MM-dd HH:mm:ss.SSS";
	
	public static final String YYYY_MM = "yyyy-MM";
	
	public static final String MM_DD = "MM-dd";
	
	public static final String YYYY_MM_DD_HH_MM_SS_EU = "yyyy/MM/dd HH:mm:ss";
	
	public static final String YYYY_MM_DD_EU = "yyyy/MM/dd";
	
	public static final String YYYY_MM_EU = "yyyy/MM";
	
	public static final String MM_DD_YYYY_EU = "MM/dd/yyyy";
	
	public static final String HH_MM_SS = "HH:mm:ss";
	
	public static final String YYYYMMDDHHMMSSMS = "yyyyMMddHHmmssSSS";
	
	// ----- formatter -----
	
	public static final FastDateFormat YYYY_MM_DD_FORMAT = FastDateFormat.getInstance(YYYY_MM_DD);
	
	public static final FastDateFormat YYYY_MM_DD_HH_MM_FORMAT = FastDateFormat
			.getInstance(YYYY_MM_DD_HH_MM);
	
	public static final FastDateFormat YYYY_MM_DD_HH_MM_SS_FORMAT = FastDateFormat
			.getInstance(YYYY_MM_DD_HH_MM_SS);
	
	public static final FastDateFormat YYYY_MM_DD_HH_MM_SS_MS_FORMAT = FastDateFormat
			.getInstance(YYYY_MM_DD_HH_MM_SS_MS);
	
	public static final FastDateFormat YYYY_MM_DD_HH_MM_SS_EU_FORMAT = FastDateFormat
			.getInstance(YYYY_MM_DD_HH_MM_SS_EU);
	
	public static final FastDateFormat YYYY_MM_DD_EU_FORMAT = FastDateFormat
			.getInstance(YYYY_MM_DD_EU);
	
	public static final FastDateFormat YYYYMMDDHHMMSSMS_FORMAT = FastDateFormat
			.getInstance(YYYYMMDDHHMMSSMS);
	
	
	public static final long DAY_MILLI_SECONDS = 24 * 60 * 60 * 1000;
	
	
	// ------- date to string utils -----------
	
	public static String format(Date date, String pattern) {
		return DateFormatUtils.format(date, pattern);
	}
	
	
	public static String format(Date date, Format formatter) {
		Assert.notNull(date, "The date must not be null.");
		Assert.notNull(formatter, "The formatter must not be null.");
		return formatter.format(date);
	}
	
	
	public static String formatYMD(Date date) {
		return format(date, YYYY_MM_DD_FORMAT);
	}
	
	
	public static String formatYMDHM(Date date) {
		return format(date, YYYY_MM_DD_HH_MM_FORMAT);
	}
	
	
	public static String formatYMDHMS(Date date) {
		return format(date, YYYY_MM_DD_HH_MM_SS_FORMAT);
	}
	
	
	public static String formatEuYMD(Date date) {
		return format(date, YYYY_MM_DD_EU_FORMAT);
	}
	
	
	public static String formatEuYMDHMS(Date date) {
		return format(date, YYYY_MM_DD_HH_MM_SS_EU_FORMAT);
	}
	
	
	public static String formatFull(Date date) {
		return format(date, YYYYMMDDHHMMSSMS_FORMAT);
	}
	
	
	// ---- string to date ------
	
	public static Date parse(String dateStr, FastDateFormat formatter) throws ParseException {
		Assert.hasText(dateStr, "The dateStr must not be null or empty.");
		Assert.notNull(formatter, "The formatter must not be null.");
		return formatter.parse(dateStr);
	}
	
	
	public static Date parse(String dateStr, String pattern) throws ParseException {
		FastDateFormat formatter = FastDateFormat.getInstance(pattern);
		return parse(dateStr, formatter);
	}
	
	
	/**
	 * 解析字符串成Date类型，忽略异常信息；出现异常时返回null
	 * @param dateStr
	 * @param pattern
	 * @return
	 */
	public static Date parseSafe(String dateStr, String pattern) {
		try {
			return parse(dateStr, pattern);
		}
		catch (ParseException ignore) {
			return null;
		}
	}
	
	
	public static Date parseYMD(String dateStr) throws ParseException {
		return parse(dateStr, YYYY_MM_DD_FORMAT);
	}
	
	
	//////////////////////////////////////////////////////////////
	// ------------------- other utils ---------------------------
	//////////////////////////////////////////////////////////////
	
	/**
	 * 增加指定天数，返回一个新的日期，原日期不会被改变
	 * @param date 原日期
	 * @param amount 指定天数，可以是负数（负数则往前减指定天数）
	 * @return 新的日期对象
	 */
	public static Date addDays(Date date, int amount) {
		return org.apache.commons.lang3.time.DateUtils.addDays(date, amount);
	}
	
	
	public static Date getWorkDaysAfter(Date date, int amount) {
		int week = amount / 5;
		int day = amount % 5;
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		// 获取这个星期的第几天
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		int plusDay = 0;
		if (day_of_week != 0) {
			// 除以4
			int tmp = day_of_week >> 2;
			if (tmp == 0) {
				// 周一到周三，周数乘以7+剩余天数
				plusDay = week * WEEKS + day;
			} else {
				// 周四到周六，周数乘以7+剩余天数+当周的周六周日两天
				plusDay = week * WEEKS + day + 2;
			}
		} else {
			// 跳过周日本身
			plusDay = week * WEEKS + day + 1;
		}
		c.add(Calendar.DAY_OF_MONTH, plusDay);
		return c.getTime();
	}
	
	
	public static Date getWorkDaysAgo(Date date, int amount) {
		int week = amount / 5;
		int day = amount % 5;
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		int substactDay = WEEKS * week;
		if (day_of_week > 0 && day_of_week <= 2) {
			// 周一周二,再去除周六日
			substactDay += day + 2;
		} else if (day_of_week == 0 || day_of_week == 6) {
			substactDay += day + 1;
		} else {
			substactDay += day;
		}
		// 减去相应的时间
		c.add(Calendar.DAY_OF_MONTH, -substactDay);
		return c.getTime();
	}
	
	
	/**
	 * 获取指定时间月份的第一天
	 * @param date
	 * @return
	 */
	public static Date beginTimeOfMonth(Date date) {
		return org.apache.commons.lang3.time.DateUtils.truncate(date, Calendar.MONTH);
	}
	
	
	/**
	 * 获取指定时间月份的最后一天
	 * @param date
	 * @return
	 */
	public static Date endTimeOfMonth(Date date) {
		// 返回的是下一个月的第一天
		Date nextMonthFirstDay = org.apache.commons.lang3.time.DateUtils.ceiling(date, Calendar.MONTH);
		return org.apache.commons.lang3.time.DateUtils.addMilliseconds(nextMonthFirstDay, -1);
	}
	
	
	private static int getFragment(final Date date, final int fragment) {
		Assert.notNull(date, "The date must not be null");
		
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(fragment);
	}
	
	/**
	 * 返回指定时间的年份值 eg:2015-05-23 return 2015
	 * @param date 不能为null
	 * @return
	 */
	public static int getYear(Date date) {
		return getFragment(date, Calendar.YEAR);
	}
	
	
	/**
	 * 返回指定时间的月份值 eg:2015-05-23 return 5 
	 * @param date 不能为null
	 * @return 1~12
	 */
	public static int getMonth(Date date) {
		return getFragment(date, Calendar.MONTH) + 1;
	}
	
	/**
	 * 返回指定时间的日期值 eg:2015-05-23 return 23
	 * @param date 不能为null
	 * @return
	 */
	public static int getDay(Date date) {
		return getFragment(date, Calendar.DAY_OF_MONTH);
	}
	
	
	/**
	 * 返回两个时间直接的间隔时间（秒）
	 * @param begin 开始时间
	 * @param end 结束时间
	 * @return
	 */
	public static long elapsedSeconds(Date begin, Date end) {
		return elapsedInFragment(begin, end, Calendar.SECOND);
	}
	
	/**
	 * 返回两个时间直接的间隔时间（分）
	 * @param begin
	 * @param end
	 * @return
	 */
	public static long elapsedMinutes(Date begin, Date end) {
		return elapsedInFragment(begin, end, Calendar.MINUTE);
	}
	
	/**
	 * 返回两个时间直接的间隔时间（时）
	 * @param begin
	 * @param end
	 * @return
	 */
	public static long elapsedHours(Date begin, Date end) {
		return elapsedInFragment(begin, end, Calendar.HOUR);
	}
	
	
	public static long elapsedDays(Date begin, Date end) {
		return elapsedInFragment(begin, end, Calendar.DATE);
	}
	
	public static long elapsedInFragment(final Date begin, final Date end, final int fragment) {
		Assert.notNull(begin, "The date begin must not be null");
		Assert.notNull(end, "The date end must not be null");
		
		long beginTime = begin.getTime();
		long endTime = end.getTime();
		long elapsedMilliSeconds = endTime - beginTime;
		long elapsed = 0L;
		
		switch (fragment) {
			case Calendar.SECOND:
				elapsed = elapsedMilliSeconds / 1000L;
				break;
			case Calendar.MINUTE:
				elapsed = elapsedMilliSeconds / (60L * 1000L);
				break;
			case Calendar.HOUR:
				elapsed = elapsedMilliSeconds / (60L * 60L * 1000L);
				break;
			case Calendar.DATE:
				elapsed = elapsedMilliSeconds / (24L * 60L * 60L * 1000L);
				break;
			default:
				break;
		}
		return elapsed;
	}
	
	
	public static void main(String[] args) throws Exception {
		Date date2 = new Date();
		Date start = parse("2015-12-06 21:25:00", YYYY_MM_DD_HH_MM_SS);
		
		System.out.println(elapsedMinutes(start ,date2));
		
//		System.out.println(getYear(date2));
//		System.out.println(getMonth(date2));
//		System.out.println(getDay(date2));
//		System.out.println(formatYMD(getWorkDaysAfter(date2, 1)));
	}
	
}
