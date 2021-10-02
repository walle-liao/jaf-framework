package com.jaf.framework.components.utils.date;

import com.jaf.framework.components.utils.Assert;
import com.jaf.framework.components.utils.common.StringUtils;
import com.jaf.framework.components.utils.math.BigDecimalUtil;
import org.apache.commons.lang3.time.FastDateFormat;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Calendar.*;

public class DateUtils {
	public final static String YYYY = "yyyy";
	public final static String MM = "MM";
	public final static String DD = "dd";
	public static final String YEAR_MONTH_DAY_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public static final String YEAR_MONTH = "yyyy-MM";
	public static final String SPLIT_CHAR = "-";
	public static final String YEAR_MONTH_DAY = "yyyy-MM-dd";
	public static final String MONTH_DAY = "MM-dd";
	public static final String YEAR_MONTH_EU = "yyyy/MM";
	public static final String MONTH_DAY_YEAR_EU = "MM/dd/yyyy";
	public static final String YEAR_MONTH_DAY_EU = "yyyy/MM/dd";
	public static final String YEAR_MONTH_DAY_H_M_S_EU = "yyyy/MM/dd HH:mm:ss";
	public final static String HH_MM_SS = "HH:mm:ss";
	public static String YEAR_MONTH_DAY_HH_MM = "yyyy-MM-dd HH:mm";
	public static final String YEARMONTHDAY = "yyyyMMdd";
	public static final String CURRENT_TIME = "yyyyMMddHHmmss";
	public static final String CURRENT_TIME_HOUR = "yyyyMMddHH";
	public static final String TIMESTAMP = "yyyyMMddHHmmssSSS";
	public final static String TIME_FULL_PATTERN = "yyyy-MM-dd HH:mm:ss SSS";
	public final static String YY_MM_DD = "yyMMdd";
	public final static String YY_MM_DD_HH = "yyMMddHH";

	private static String QONE = "QONE";
	private static String QTWO = "QTWO";
	private static String QTHREE = "QTHREE";
	private static String QFOUR = "QFOUR";
	public static final int WEEKS = 7;

	private DateUtils() {

	}

	public static Date now() {
		return new GregorianCalendar().getTime();
	}

	public static Date now(String pattern) {
		return DateUtils.toDate(nowString(pattern));
	}

	public static String nowString() {
		return nowString(DateUtils.YEAR_MONTH_DAY);
	}

	public static String nowString(String pattern) {
		Date date = now();
		return DateUtils.toString(date, pattern);
	}

	public static LocalDate toLocalDate(Date date) {
		Objects.requireNonNull(date);
		return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public static YearMonth toYearMonth(Date date) {
		Objects.requireNonNull(date);
		return LocalDateUtils.toYearMonth(toLocalDate(date));
	}

	public static Date toDate(String time, String pattern) {
		Assert.notNull(time);
		Assert.notNull(pattern);
		return toCalendar(time, pattern).getTime();
	}

	public static Date plusDays(Date date, long daysToAdd) {
		Objects.requireNonNull(date);
		return LocalDateUtils.toDate(toLocalDate(date).plusDays(daysToAdd));
	}

	public static Date plusWeeks(Date date, long weeksToAdd) {
		Objects.requireNonNull(date);
		return LocalDateUtils.toDate(toLocalDate(date).plusWeeks(weeksToAdd));
	}

	public static Date plusMonths(Date date, long monthsToAdd) {
		Objects.requireNonNull(date);
		return LocalDateUtils.toDate(toLocalDate(date).plusMonths(monthsToAdd));
	}

	public static Date plusYears(Date date, long yearsToAdd) {
		Objects.requireNonNull(date);
		return LocalDateUtils.toDate(toLocalDate(date).plusYears(yearsToAdd));
	}

	public static BigDecimal calculateEffectMonth(Month month, Date startDate, Date endDate) {
        if(month == null || startDate == null || endDate == null)
            return BigDecimal.ZERO;

        Date monthFirstDay = month.toDate();
        Date monthEndDay = month.atEndDay();
	    if(startDate.before(monthFirstDay) || startDate.after(monthEndDay))
	        throw new IllegalArgumentException("开始日期不能小于月份[ " + month.toString() + " ]第一天");

	    if(endDate.before(monthFirstDay) || endDate.after(monthEndDay))
	        throw new IllegalArgumentException("结束日期不能大于月份[ " + month.toString() + " ]最后一天");

        Calendar startCalender = Calendar.getInstance();
        startCalender.setTime(startDate);
        int startDateNo = startCalender.get(Calendar.DAY_OF_MONTH);

	    Calendar endCalendar = Calendar.getInstance();
	    endCalendar.setTime(endDate);
	    int endDateNo = endCalendar.get(Calendar.DAY_OF_MONTH);

        int dayOfMonth = month.getDayOfMonth();
        return BigDecimalUtil.divide(new BigDecimal((endDateNo - startDateNo + 1)), new BigDecimal(dayOfMonth), 2);
    }

	public static GregorianCalendar toCalendar(String time, String pattern) {
		Assert.notNull(time);
		Assert.notNull(pattern);

		SimpleDateFormat format = new SimpleDateFormat(pattern);
		Date date = null;
		try {
			date = format.parse(time);
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			return calendar;
		} catch (ParseException e) {
			throw new RuntimeException(
					"[Pase Exception]: the time string doesn't match for pattern.");
		}
	}
	
	public static Calendar toCalendar(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c;
	}

	/**
	 * 计算两个日期之间相差的月数
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int getMonths(Date date1, Date date2) {
		int iMonth = 0;
		int flag = 0;
		try {
			Calendar objCalendarDate1 = Calendar.getInstance();
			objCalendarDate1.setTime(date1);

			Calendar objCalendarDate2 = Calendar.getInstance();
			objCalendarDate2.setTime(date2);

			if (objCalendarDate2.equals(objCalendarDate1))
				return 0;
			if (objCalendarDate1.after(objCalendarDate2)) {
				Calendar temp = objCalendarDate1;
				objCalendarDate1 = objCalendarDate2;
				objCalendarDate2 = temp;
			}
			if (objCalendarDate2.get(Calendar.DAY_OF_MONTH) < objCalendarDate1
					.get(Calendar.DAY_OF_MONTH))
				flag = 1;

			if (objCalendarDate2.get(Calendar.YEAR) > objCalendarDate1
					.get(Calendar.YEAR))
				iMonth = ((objCalendarDate2.get(Calendar.YEAR) - objCalendarDate1
						.get(Calendar.YEAR))
						* 12
						+ objCalendarDate2.get(Calendar.MONTH) - flag)
						- objCalendarDate1.get(Calendar.MONTH);
			else
				iMonth = objCalendarDate2.get(Calendar.MONTH)
						- objCalendarDate1.get(Calendar.MONTH) - flag;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return iMonth;
	}

	/**
	 * 提供从String类型到Date类型的类型转化，目前自动支持 "yyyy-MM-dd"、"yyyy-MM"、
	 * "yyyy-MM-dd HH:mm:ss"、"MM-dd"等4种日期格式的自动转化
	 * 
	 * @param time
	 * @return
	 */
	public static Date toDate(String time) {
		Assert.notNull(time);
		for (String key : defaultDateFormatMap.keySet()) {
			if (isDateFormat(time, key)) {
				return DateUtils.toDate(time, defaultDateFormatMap.get(key));
			}
		}
		throw new RuntimeException("just support format : "
				+ StringUtils.collectionToDelimitedString(
						defaultDateFormatMap.values(), ",") + " - " + time);
	}

	public static String toString(Date date, String pattern) {
		if(date==null){
			return null;
		}
		Assert.notNull(pattern);
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	public static String toString(Date date) {
		return toString(date, YEAR_MONTH_DAY_HH_MM_SS);
	}

	/**
	 * 比较两个 Date 对象表示的时间值（从历元至现在的毫秒偏移量）。
	 * 
	 * @param d1
	 * @param d2
	 * @return 如果 d1 表示的时间等于 d2 表示的时间，则返回 0 值；如果此 d1 的时间在d2表示的时间之前，则返回小于 0 的值；如果
	 *         d1 的时间在 d2 表示的时间之后，则返回大于 0 的值。
	 * 
	 */
	public static int compare(Date d1, Date d2) {
		Assert.notNull(d1);
		Assert.notNull(d2);

		Calendar c1 = new GregorianCalendar();
		Calendar c2 = new GregorianCalendar();
		c1.setTime(d1);
		c2.setTime(d2);

		return c1.compareTo(c2);
	}

	/**
	 * 比较两个 Date 对象表示的日期值（仅仅比较日期,忽略时分秒）。 -wuwm
	 * 
	 * @param d1
	 * @param d2
	 * @return 如果 d1 表示的日期等于 d2 表示的日期，则返回 0 值；如果此 d1 的日期在d2表示的日期之前，则返回小于 0 的值；如果
	 *         d1 的日期在 d2 表示的日期之后，则返回大于 0 的值。
	 * 
	 */
	public static int compareDate(Date d1, Date d2) {
		Assert.notNull(d1);
		Assert.notNull(d2);

		d1 = DateUtils.toDate(DateUtils.toString(d1, DateUtils.YEAR_MONTH_DAY),
				DateUtils.YEAR_MONTH_DAY);
		d2 = DateUtils.toDate(DateUtils.toString(d2, DateUtils.YEAR_MONTH_DAY),
				DateUtils.YEAR_MONTH_DAY);
		Calendar c1 = new GregorianCalendar();
		Calendar c2 = new GregorianCalendar();
		c1.setTime(d1);
		c2.setTime(d2);

		return c1.compareTo(c2);
	}
	public static int compareMonth(Date month1, Date month2) {
		Assert.notNull(month1);
		Assert.notNull(month2);
		
		month1 = DateUtils.toDate(DateUtils.toString(month1, DateUtils.YEAR_MONTH),
				DateUtils.YEAR_MONTH);
		month2 = DateUtils.toDate(DateUtils.toString(month2, DateUtils.YEAR_MONTH),
				DateUtils.YEAR_MONTH);
		Calendar c1 = new GregorianCalendar();
		Calendar c2 = new GregorianCalendar();
		c1.setTime(month1);
		c2.setTime(month2);
		
		return c1.compareTo(c2);
	}

	/**
	 * 根据年月获取一个月的开始时间（第一天的凌晨）
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date beginTimeOfMonth(int year, int month) {
		Calendar first = new GregorianCalendar(year, month - 1, 1, 0, 0, 0);
		return first.getTime();
	}
	
	/**
	 * 根据年月获取一个月的开始时间（第一天的凌晨）
	 * 
	 * @return
	 */
	public static Date beginTimeOfMonth(Date date) {
		Calendar first = new GregorianCalendar(DateUtils.getYear(date),DateUtils.getMonth(date)-1, 1, 0, 0, 0);
		return first.getTime();
	}

	public static Date nextMonthStartTime(Date month){
		return DateUtils.beginTimeOfMonth(getYear(month), getMonth(month)+1);
	}
	/**
	 * 根据年月获取一个月的结束时间（最后一天的最后一毫秒）
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date endTimeOfMonth(int year, int month) {
		Calendar last = new GregorianCalendar(year, month , 1, 0, 0, 0);
		last.add(Calendar.MILLISECOND, -1);
		return last.getTime();
	}
	/**
	 * 根据年月获取一个月的结束时间（最后一天的最后一毫秒）
	 * 
	 * @return
	 */
	public static Date endTimeOfMonth(Date date) {
		return endTimeOfMonth(DateUtils.getYear(date),DateUtils.getMonth(date));
	}

	/**
	 * 获取前preDays天的Date对象
	 * 
	 * @param date
	 * @param preDays
	 * @return
	 */
	public static Date preDays(Date date, int preDays) {
		if(date==null){return null;}
		GregorianCalendar c1 = new GregorianCalendar();
		c1.setTime(date);
		GregorianCalendar cloneCalendar = (GregorianCalendar) c1.clone();
		cloneCalendar.add(Calendar.DATE, -preDays);
		return cloneCalendar.getTime();
	}

	/**
	 * 获取后nextDays天的Date对象
	 * 
	 * @param date
	 * @param nextDays
	 * @return
	 */
	public static Date nextDays(Date date, int nextDays) {
		if(date==null){return null;}
		GregorianCalendar c1 = new GregorianCalendar();
		c1.setTime(date);
		GregorianCalendar cloneCalendar = (GregorianCalendar) c1.clone();
		cloneCalendar.add(Calendar.DATE, nextDays);
		return cloneCalendar.getTime();
	}

	public static Date nextMonths(Date date, int nextMonth) {
		GregorianCalendar c1 = new GregorianCalendar();
		c1.setTime(date);
		GregorianCalendar cloneCalendar = (GregorianCalendar) c1.clone();
		cloneCalendar.add(Calendar.MONTH, nextMonth);
		return cloneCalendar.getTime();
	}

	public static Date preMonths(Date date, int preMonth) {
		GregorianCalendar c1 = new GregorianCalendar();
		c1.setTime(date);
		GregorianCalendar cloneCalendar = (GregorianCalendar) c1.clone();
		cloneCalendar.add(Calendar.MONTH, -preMonth);
		return cloneCalendar.getTime();
	}

	public static long getDiffMillis(Date d1, Date d2) {
		Assert.notNull(d1);
		Assert.notNull(d2);

		long diff = d1.getTime() - d2.getTime();

		return diff;
	}

	/**
	 * 间隔天数
	 * 
	 * @param d1
	 * @param d2
	 * @return d1 - d2 实际天数,如果 d1 表示的时间等于 d2 表示的时间，则返回 0 值；如果此 d1
	 *         的时间在d2表示的时间之前，则返回小于 0 的值；如果 d1 的时间在 d2 表示的时间之后，则返回大于 0 的值。
	 */
	public static long dayDiff(Date d1, Date d2) {
		Assert.notNull(d1);
		Assert.notNull(d2);

		Calendar c1 = new GregorianCalendar();
		Calendar c2 = new GregorianCalendar();

		c1.setTime(d1);
		c2.setTime(d2);

		long diffDays = getDiffDays(c1, c2);

		return diffDays;
	}

	/**
	 * 间隔毫秒数
	 * 
	 * @param d1
	 * @param d2
	 * @return d1 - d2 实际毫秒数,如果 d1 表示的时间等于 d2 表示的时间，则返回 0 值；如果此 d1
	 *         的时间在d2表示的时间之前，则返回小于 0 的值；如果 d1 的时间在 d2 表示的时间之后，则返回大于 0 的值。
	 */
	public static long diffMillis(Date d1, Date d2) {
		Assert.notNull(d1);
		Assert.notNull(d2);

		Calendar c1 = new GregorianCalendar();
		Calendar c2 = new GregorianCalendar();

		c1.setTime(d1);
		c2.setTime(d2);

		long diffDays = getDiffMillis(c1, c2);

		return diffDays;
	}

	
	/**
	 * 间隔毫秒数
	 * 
	 * @param d1
	 * @param d2
	 * @return d1 - d2 实际毫秒数,如果 d1 表示的时间等于 d2 表示的时间，则返回 0 值；如果此 d1
	 *         的时间在d2表示的时间之前，则返回小于 0 的值；如果 d1 的时间在 d2 表示的时间之后，则返回大于 0 的值。
	 */
	public static long diffMinute(Date d1, Date d2) {
		Assert.notNull(d1);
		Assert.notNull(d2);
		Calendar c1 = new GregorianCalendar();
		Calendar c2 = new GregorianCalendar();
		c1.setTime(d1);
		c2.setTime(d2);
		long diffDays = getDiffMillis(c1, c2);
		long minute = diffDays / 1000/60  ;
		return minute;
	}
	
	
	/**
	 * 获取间隔时间
	 * 
	 * @param d1
	 * @param d2
	 * @return HH:MM:SS,返回时间间隔的绝对值，没有负数
	 */
	public static String getDiffs(Date d1, Date d2) {
		long diffMillis = DateUtils.getDiffMillis(d1, d2);
		long diffHours = diffMillis / (60L * 60L * 1000L);
		long diffMinutes = diffMillis / (60L * 1000L) % 60;
		long diffSeconds = diffMillis / 1000L % 60;
		diffHours = Math.abs(diffHours);
		diffMinutes = Math.abs(diffMinutes);
		diffSeconds = Math.abs(diffSeconds);
		StringBuffer temp = new StringBuffer();
		temp.append(diffHours < 10 ? "0" + diffHours : diffHours);
		temp.append(":");
		temp.append(diffMinutes < 10 ? "0" + diffMinutes : diffMinutes);
		temp.append(":");
		temp.append(diffSeconds < 10 ? "0" + diffSeconds : diffSeconds);
		return temp.toString();
	}

	public static boolean isDateFormat(String date) {
		Assert.notNull(date);
		for (String key : defaultDateFormatMap.keySet()) {
			if (isDateFormat(date, key)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isDateFormat(String date, String format) {
		Assert.notNull(date);
		return StringUtils.isDefinedPattern(date, format);
	}

	public static Date getNowDate() {
		Date now = new Date();
		return toDate(toString(now, YEAR_MONTH_DAY), YEAR_MONTH_DAY);
	}

	/**
	 * 根据日期返回日期中的年. wuwm
	 * 
	 * @param d
	 * @return int
	 */
	public static int getYear(Date d) {
		Assert.notNull(d);
		String dateStr = DateUtils.toString(d, DateUtils.YEAR_MONTH); // yyyy-MM
		return Integer.parseInt(dateStr.split(DateUtils.SPLIT_CHAR)[0]);
	}

	/**
	 * 根据日期返回日期中的年.
	 * 
	 * @param d
	 * @return int
	 */
	public static int getMonth(Date d) {
		Assert.notNull(d);
		String dateStr = DateUtils.toString(d, DateUtils.YEAR_MONTH); // yyyy-MM
		return Integer.parseInt(dateStr.split(DateUtils.SPLIT_CHAR)[1]);
	}

	/**
	 * 根据日期返回日期中的日.
	 * 
	 * @param d
	 * @return int
	 */
	public static int getDay(Date d) {
		Assert.notNull(d);
		String dateStr = DateUtils.toString(d, DateUtils.YEAR_MONTH_DAY); // yyyy-MM-dd
		return Integer.parseInt(dateStr.split(DateUtils.SPLIT_CHAR)[2]);
	}

	public static boolean isEnDateFormat(String str) {
		String rex = "((((02|2)\\/29)\\/(19|20)(([02468][048])|([13579][26])))|((((0?[1-9])|(1[0-2]))\\/((0?[1-9])|(1\\d)|(2[0-8])))|((((0?[13578])|(1[02]))\\/31)|(((0?[1,3-9])|(1[0-2]))\\/(29|30))))\\/((20[0-9][0-9])|(19[0-9][0-9])|(18[0-9][0-9])))";
		Pattern regex = Pattern.compile(rex);
		Matcher matcher = regex.matcher(str);
		boolean isMatched = matcher.matches();
		return isMatched;
	}

	private static Map<String, String> defaultDateFormatMap = new HashMap<String, String>();
	static {
		defaultDateFormatMap.put("[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}",
				DateUtils.YEAR_MONTH_DAY);
		defaultDateFormatMap.put("[0-9]{4}/[0-9]{1,2}/[0-9]{1,2}",
				DateUtils.YEAR_MONTH_DAY_EU);
		defaultDateFormatMap
				.put("[0-9]{4}-[0-9]{1,2}-[0-9]{1,2} [0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}",
						DateUtils.YEAR_MONTH_DAY_HH_MM_SS);
		defaultDateFormatMap
				.put("[0-9]{4}/[0-9]{1,2}/[0-9]{1,2} [0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}",
						DateUtils.YEAR_MONTH_DAY_H_M_S_EU);
		defaultDateFormatMap.put("[0-9]{4}-[0-9]{1,2}", DateUtils.YEAR_MONTH);
		defaultDateFormatMap
				.put("[0-9]{4}/[0-9]{1,2}", DateUtils.YEAR_MONTH_EU);
		defaultDateFormatMap
				.put("((((02|2)/29)/(19|20)(([02468][048])|([13579][26])))|((((0?[1-9])|(1[0-2]))/((0?[1-9])|(1\\d)|(2[0-8])))|((((0?[13578])|(1[02]))/31)|(((0?[1,3-9])|(1[0-2]))/(29|30))))/((20[0-9][0-9])|(19[0-9][0-9])|(18[0-9][0-9])))",
						DateUtils.MONTH_DAY_YEAR_EU);
	}

	public static String formatYMD(Date date) {
		if(date == null)
			return "";

		return FastDateFormat.getInstance("yyyy-MM-dd").format(date);
	}

	/*
	 * 计算时间差 双休日除外 取绝对值 忽略时分秒
	 */
	public static int getWorkingDay(Date d1, Date d2) {
		d1 = DateUtils.toDate(DateUtils.toString(d1, DateUtils.YEAR_MONTH_DAY),
				DateUtils.YEAR_MONTH_DAY);
		d2 = DateUtils.toDate(DateUtils.toString(d2, DateUtils.YEAR_MONTH_DAY),
				DateUtils.YEAR_MONTH_DAY);
		Calendar startCal = Calendar.getInstance();
		Calendar endCal = Calendar.getInstance();
		if (d1.before(d2)) {
			startCal.setTime(d1);
			endCal.setTime(d2);
		} else {
			return 0;// 结束时间 大于开始时间 返回 0
		}
		int days = 0;
		startCal.add(GregorianCalendar.DATE, 1);// 当天不算
		while (startCal.before(endCal) || startCal.equals(endCal)) {
			int week = startCal.get(Calendar.DAY_OF_WEEK);
			if (week != 1 && week != 7) {
				days++;
			}
			startCal.add(GregorianCalendar.DATE, 1);
		}
		return days;
	}

	/**
	 * 追加秒
	 * 
	 * @param targetDate
	 * @param second
	 * @return
	 */
	public static Date addSecond(Date targetDate, int second) {
		final Calendar c = Calendar.getInstance();
		final long afterTime = second * 1000L;
		final long time = targetDate.getTime() + afterTime;
		c.setTimeInMillis(time);
		return c.getTime();
	}
	
	/**
	 * 追加毫秒
	 * 
	 * @param targetDate
	 * @return
	 */
	public static Date addMillis(Date targetDate, long millis) {
		final Calendar c = Calendar.getInstance();
		final long time = targetDate.getTime() + millis;
		c.setTimeInMillis(time);
		return c.getTime();
	}

	public static long getDiffDays(Calendar c1, Calendar c2) {
		Assert.notNull(c1);
		Assert.notNull(c2);

		Calendar c1Copy = new GregorianCalendar(c1.get(YEAR), c1.get(MONTH),
				c1.get(DATE));
		Calendar c2Copy = new GregorianCalendar(c2.get(YEAR), c2.get(MONTH),
				c2.get(DATE));

		long diffMillis = getDiffMillis(c1Copy, c2Copy);

		long dayMills = 24L * 60L * 60L * 1000L;
		long diffDays = diffMillis / dayMills;

		return diffDays;
	}

	public static long getDiffMillis(Calendar c1, Calendar c2) {
		Assert.notNull(c1);
		Assert.notNull(c2);

		long diff = c1.getTimeInMillis() - c2.getTimeInMillis();

		return diff;
	}

	public static String format(Date date, String pattern) {
		if (date == null)
			return "";
		else
			return toString(date, pattern);
	}

	/**
	 * 默认把日期格式化成yyyy-mm-dd格式
	 * 
	 * @param date
	 * @return
	 */
	public static String format(Date date) {
		if (date == null)
			return "";
		else
			return toString(date, YEAR_MONTH_DAY);
	}

	//
	/**
	 * 根据年月得到这个月最后一天
	 * 
	 * @param year
	 * @param months
	 * @return
	 */
	public static String getDateStr(int year, int months) {
		Calendar cal = Calendar.getInstance();
		// 不加下面2行，就是取当前时间前一个月的第一天及最后一天
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, months);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		Date lastDate = cal.getTime();
		return format(lastDate);
	}

	/**
	 * 得到当前月的第一天
	 * 
	 * @return
	 */
	public static String getNowMonthDay() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return format(cal.getTime());
	}
	
	/**
	 * 判断当前日期是否为当前月的第一天
	 * 
	 * @return
	 */
	public static boolean isTheFirstDayOfTheMonth() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.DAY_OF_MONTH) == 1;
	}
	
	/** 
     * 判断当前日期是否为当前月的最后一天 
     *  
     * @return 
     */  
    public static boolean isTheLastDayOfTheMonth() {  
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_MONTH) == calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    } 

	/**
	 * 根据所传季度查询季度区间
	 * 
	 * @return
	 */
	public String[] getDateQ(String year, String qStr) {
		String[] dataStr = new String[2];
		if (qStr.equals(QONE)) {
			dataStr[0] = year + "-01";
			dataStr[1] = year + "-03";
		} else if (qStr.equals(QTWO)) {
			dataStr[0] = year + "-04";
			dataStr[1] = year + "-06";
		} else if (qStr.equals(QTHREE)) {
			dataStr[0] = year + "-07";
			dataStr[1] = year + "-09";
		} else if (qStr.equals(QFOUR)) {
			dataStr[0] = year + "-10";
			dataStr[1] = year + "-12";
		}
		return dataStr;
	}

	/**
	 * 得到前半年，后半年开始时间和结束时间
	 * 
	 * @param year
	 *            AGO 前半年 AFTER 后半年
	 * @param yStr
	 * @return
	 */
	public String[] getDateYear(String year, String yStr) {
		String[] dataStr = new String[2];
		if (yStr.equals("AGO")) {
			dataStr[0] = year + "-01";
			dataStr[1] = year + "-06";
		} else if (yStr.equals("AFTER")) {
			dataStr[0] = year + "-07";
			dataStr[1] = year + "-12";
		}
		return dataStr;
	}

	/**
	 * 根据所传年度
	 * 
	 * @return
	 */
	public String[] getDateYear(String year) {
		String[] dataStr = new String[2];
		dataStr[0] = year + "-01";
		dataStr[1] = year + "-12";
		return dataStr;
	}

	/**
	 * 得到当前月份的前一年所有月份
	 * 
	 * @return
	 */
	public String[] getDateYear() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.YEAR, -1);
		String time = new SimpleDateFormat("yyyy-MM").format(c.getTime());
		String[] timeStr = time.split("-");
		String[] dataStr = new String[12];
		String year = "";
		String months = "";
		int j = 1;
		for (int i = 0; i < 12; i++) {
			if (Integer.parseInt(timeStr[1]) + (i + 1) <= 12) {
				year = timeStr[0];
				if (Integer.parseInt(timeStr[1]) + (i + 1) < 10) {
					months = "0"
							+ String.valueOf(Integer.parseInt(timeStr[1])
									+ (i + 1));
				} else {
					months = String.valueOf(Integer.parseInt(timeStr[1])
							+ (i + 1));
				}
				dataStr[i] = year + "-" + months;
			} else {
				year = String.valueOf(Integer.parseInt(timeStr[0]) + 1);
				if (j < 10) {
					months = "0" + j;
				} else {
					months = String.valueOf(j);
				}
				dataStr[i] = year + "-" + months;
				j++;
			}
		}
		return dataStr;
	}

	/**
	 * 获取当前年分 样式：yyyy
	 * 
	 * @return 当前年分
	 */
	public static String getYear() {
		return format(now(), YYYY);
	}

	/**
	 * 获取当前年分 样式：yyyy
	 * 
	 * @return 当前年分
	 */
	public static int getCurrentYear() {
		return Integer.parseInt(format(now(), YYYY));
	}

	/**
	 * 获取当前月分 样式：MM
	 * 
	 * @return 当前月分
	 */
	public static String getMonth() {
		return format(now(), MM);
	}

	/**
	 * 获取当前日期号 样式：dd
	 * 
	 * @return 当前日期号
	 */
	public static String getDay() {
		return format(now(), DD);
	}
	/**
	 * 获取当前日期号 样式：dd
	 * 
	 * @return 当前日期号
	 */
	public static String getHour() {
		return format(now(), "HH");
	}

	/**
	 * 获取当前日期分钟号 样式：mm
	 *
	 * @return 当前日期分钟号
	 */
	public static String getMinute() {
		return format(now(), "mm");
	}

	/**
	 * 获取当前日期秒号 样式：ss
	 *
	 * @return 当前日期分钟号
	 */
	public static String getSecond() {
		return format(now(), "ss");
	}

	/**
	 * 按给定日期样式判断给定字符串是否为合法日期数据
	 * 
	 * @param strDate
	 *            要判断的日期
	 * @param pattern
	 *            日期样式
	 * @return true 如果是，否则返回false
	 */
	public static boolean isDate(String strDate, String pattern) {
		try {
			toDate(strDate, pattern);
			return true;
		} catch (Exception pe) {
			return false;
		}
	}

	/**
	 * 判断给定字符串是否为特定格式年份（格式：yyyy）数据
	 * 
	 * @param strDate
	 *            要判断的日期
	 * @return true 如果是，否则返回false
	 */
	public static boolean isYYYY(String strDate) {
		try {
			toDate(strDate, YYYY);
			return true;
		} catch (Exception pe) {
			return false;
		}
	}

	public static boolean isYYYY_MM(String strDate) {
		try {
			toDate(strDate, YEAR_MONTH);
			return true;
		} catch (Exception pe) {
			return false;
		}
	}

	/**
	 * 判断给定字符串是否为特定格式的年月日（格式：yyyy-MM-dd）数据
	 * 
	 * @param strDate
	 *            要判断的日期
	 * @return true 如果是，否则返回false
	 */
	public static boolean isYYYY_MM_DD(String strDate) {
		try {
			toDate(strDate, YEAR_MONTH_DAY);
			return true;
		} catch (Exception pe) {
			return false;
		}
	}

	/**
	 * 判断给定字符串是否为特定格式年月日时分秒（格式：yyyy-MM-dd HH:mm:ss）数据
	 * 
	 * @param strDate
	 *            要判断的日期
	 * @return true 如果是，否则返回false
	 */
	public static boolean isYYYY_MM_DD_HH_MM_SS(String strDate) {
		try {
			toDate(strDate, YEAR_MONTH_DAY_HH_MM_SS);
			return true;
		} catch (Exception pe) {
			return false;
		}
	}

	/**
	 * 判断给定字符串是否为特定格式时分秒（格式：HH:mm:ss）数据
	 * 
	 * @param strDate
	 *            要判断的日期
	 * @return true 如果是，否则返回false
	 */
	public static boolean isHH_MM_SS(String strDate) {
		try {
			toDate(strDate, HH_MM_SS);
			return true;
		} catch (Exception pe) {
			return false;
		}
	}

	/**
	 * 计算日期 added by taking.wang
	 * 
	 * @param date
	 *            日期
	 * @param month
	 *            月份
	 * @return
	 */
	public static Date calcDate(Date date, int month) throws Exception {
		if (null != date && null != new Integer(month)) { // 先判断当前日期和试用期是否都有数据

			Calendar cal = Calendar.getInstance();
			cal.setTime(date);

			int vYear = cal.get(Calendar.YEAR); // 得到年
			int vMonth = cal.get(Calendar.MONTH) + 1; // 得到月
			int vDay = cal.get(Calendar.DATE); // 得到日

			vMonth += month;
			if (vMonth > 12) { // 如果日期加起来大于12的话，则年份要加1
				vYear++;
				vMonth -= 12;
			}

			return toDate(vYear
					+ "-"
					+ ((String.valueOf(vMonth).length() == 1) ? "0" + vMonth
							: vMonth) + "-" + vDay, "yyyy-MM-dd");
		}
		return null;
	}

	/**
	 * d1到d2之间有多少天 比如 5月1号 5月31号有31天
	 * 
	 * @return
	 */
	public static int getDays(Date endDate, Date startDate) {
		return (int) (DateUtils.dayDiff(endDate, startDate) + 1);
	}

	/**
	 * 计算7个工作日后日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date get7WorkDays(Date date) {
		return getNWorkDays(date, 7);
	}

	/**
	 * 获取N个工作日以后的日期
	 * 
	 * @param date
	 * @param n
	 * @return
	 */
	public static Date getNWorkDays(Date date, int n) {
		int week = n / 5;
		int day = n % 5;
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;// 获取这个星期的第几天
		int plusDay = 0;
		if (day_of_week != 0) {// 周日
			int tmp = day_of_week >> 2;// 除以4
			if (tmp == 0) {// 周一到周三，周数乘以7+剩余天数
				plusDay = week * WEEKS + day;
			} else {// 周四到周六，周数乘以7+剩余天数+当周的周六周日两天
				plusDay = week * WEEKS + day + 2;
			}
		} else {
			plusDay = week * WEEKS + day + 1;// 跳过周日本身
		}
		c.add(Calendar.DAY_OF_MONTH, plusDay);

		return c.getTime();
	}

	/**
	 * 
	 * @param date
	 * @param n
	 * @return
	 */
	public static Date getNWorkDaysAgo(Date date, int n) {
		int week = n / 5;// 周数
		int day = n % 5;
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;// 获取这个星期的第几天
		int substactDay = WEEKS * week;
		if (day_of_week > 0 && day_of_week <= 2) {// 周一周二,再去除周六日
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
	 * 获取7个工作日以前的日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date get7WorkDaysAgo(Date date) {
		return getNWorkDaysAgo(date, 7);
	}

	/**
	 * 求两个时间区间的交集天数
	 * 
	 * @param from_1
	 *            区间1开始日期
	 * @param to_1
	 *            区间1结束日期
	 * @param from_2
	 *            区间2开始日期
	 * @param to_2
	 *            区间2结束日期
	 * @param withWeekend
	 *            是否包括周末
	 * @return
	 */
	public static int getDateAreaMix(Date from_1, Date to_1, Date from_2,
                                     Date to_2, boolean withWeekend) {
		Calendar start = Calendar.getInstance();
		if (from_1.before(from_2)) {
			start.setTime(from_2);
		} else {
			start.setTime(from_1);
		}
		Calendar end = Calendar.getInstance();
		if (to_1.before(to_2)) {
			end.setTime(to_1);
		} else {
			end.setTime(to_2);
		}
		if (end.before(start)) {
			return 0;
		}
		int count = 0;
		while (end.after(start) || end.equals(start)) {
			if (withWeekend) {
				count++;
			} else {
				if (start.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
						&& start.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
					count++;
				}
			}
			start.add(Calendar.DATE, 1);
		}
		return count;
	}

	/**
	 * 得到date-day
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date getDateAfterDay(Date date, int day) {
		if (date == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(date.getTime()));
		c.add(Calendar.DAY_OF_YEAR, day);
		return c.getTime();
	}
	
	/**
	 * 获取在指定日期之前的指定天数的日期
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date getDateBeforeDay(Date date, int day) {
		return DateUtils.getDateAfterDay(date, -day);
	}

	/**
	 * 得到一天开始时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date getStartDate(Date date) {
		if (date == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(date.getTime()));
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH),
				c.get(Calendar.DATE), 0, 0, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	public static Date getEndDate(Date date) {
		if (date == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(date.getTime()));
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH),
				c.get(Calendar.DATE), 23, 59, 59);
		c.set(Calendar.MILLISECOND, 999);
		return c.getTime();
	}

	/**
	 * 得到一天的开始
	 * 
	 * @param dateStr
	 * @param format
	 * @return
	 */
	public static Date getStartDate(String dateStr, String format) {
		if (dateStr == null) {
			return null;
		}
		Date date = null;
		try {
			date = getStartDate(toDate(dateStr, format));
		} catch (Exception e) {
		}
		return date;
	}

	/**
	 * 得到一天的结束
	 * 
	 * @param dateStr
	 * @param format
	 * @return
	 */
	public static Date getEndDate(String dateStr, String format) {
		if (dateStr == null) {
			return null;
		}
		Date date = null;
		try {
			date = getEndDate(toDate(dateStr, format));
		} catch (Exception e) {
		}
		return date;
	}

	

	public static long getIntevalDays(String startDate, String endDate) {
		try {
			return getIntevalDays(toDate(startDate, YEAR_MONTH_DAY),
					toDate(endDate, YEAR_MONTH_DAY));
		} catch (Exception ee) {
			return 0l;
		}
	}

	public static long getIntevalDays(Date startDate, Date endDate) {
		try {
			java.util.Calendar startCalendar = java.util.Calendar.getInstance();
			java.util.Calendar endCalendar = java.util.Calendar.getInstance();

			startCalendar.setTime(startDate);
			endCalendar.setTime(endDate);
			long diff = endCalendar.getTimeInMillis()
					- startCalendar.getTimeInMillis();

			return (diff / (1000 * 60 * 60 * 24));
		} catch (Exception ee) {
			return 0l;
		}
	}
	
	/**
	 * 获取间隔小时数
	 *
	 * @param d1
	 * @param d2
	 * @return HH:MM:SS,返回时间间隔的绝对值，没有负数
	 */
	public static long hoursDiffs(Date d1, Date d2) {
		long diffMillis = DateUtils.getDiffMillis(d1, d2);
		long diffHours = diffMillis / (60L * 60L * 1000L);
		return Math.abs(diffHours);
	}
	
    /** 
     * 当前季度的开始时间，即2012-01-1 00:00:00,000 
     * 
     * @return 
     */ 
    public static Date getCurrentQuarterStartTime() {
        return getQuarterStartTime(new Date());
    }
    
    
    public static Date getQuarterStartTime(Date date) {
    	Calendar c = Calendar.getInstance();
    	c.setTime(date);
        int currentMonth = c.get(Calendar.MONTH) + 1;
        try { 
            if (currentMonth >= 1 && currentMonth <= 3) 
                c.set(Calendar.MONTH, 0);
            else if (currentMonth >= 4 && currentMonth <= 6) 
                c.set(Calendar.MONTH, 3);
            else if (currentMonth >= 7 && currentMonth <= 9) 
                c.set(Calendar.MONTH, 4);
            else if (currentMonth >= 10 && currentMonth <= 12) 
                c.set(Calendar.MONTH, 9);
            c.set(Calendar.DATE, 1);
            c.set(Calendar.HOUR, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);
        } catch (Exception e) {
            e.printStackTrace(); 
        } 
        return c.getTime();
    }

	
	/**
	 * 传入两个日期，获取较大的日期
	 * 如果两个日期对象都不为null，返回较大的日期，如果两个日期相等，随机返回一个日期
	 * 如果两个日期中其中一个日期为null，返回不为null的日期对象
	 * 如果两个日期对象都为null，返回null
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static Date getMaxDate(Date d1, Date d2) {
		return d1 != null && d2 != null 
					? (d1.after(d2) ? d1 : d2) 
					: (d1 != null ? d1 : (d2 != null ? d2 : null));
	}
    
	/**
	 * 获取某月的最早一天
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getFirstDayOfMonth(int year, int month){
		final Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR,year);
		cal.set(Calendar.MONTH, month-1);
		final int firstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, firstDay);
		return getStartDate(cal.getTime());
	}
	/**
	 * 获取某月的最早一天
	 * @return
	 */
	public static Date getFirstDayOfMonth(Date date){
		return getFirstDayOfMonth(getYear(date), getMonth(date));
	}
	/**
	 * 获取某月的最后一天
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getLastDayOfMonth(int year, int month){
		final Calendar cal = Calendar.getInstance();
		//设置年份
		cal.set(Calendar.YEAR,year);
		//设置月份
		cal.set(Calendar.MONTH, month-1);
		//获取某月最大天数
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		//设置日历中月份的最大天数
		cal.set(Calendar.DAY_OF_MONTH, lastDay);
		return getEndDate(cal.getTime());
	}
	/**
	 * 获取某月的最后一天
	 * @return
	 */
	public static Date getLastDayOfMonth(Date date){
		return getLastDayOfMonth(getYear(date), getMonth(date));
	}
	
	/**
	 * 获取日期是星期几
	 * @param date
	 * @return
	 */
	public static String getWeekOfDate(Date date) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
	
	/**
	 * 获取日期是星期几
	 * @param date
	 * @return
	 */
	public static int getWeek(Date date) {
		//String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return w;
    }
	
	public static Date max(Date d1, Date d2){
		return DateUtils.compare(d1, d2)==1 ? d1 : d2;
	}
	public static Date min(Date d1, Date d2){
		return DateUtils.compare(d1, d2)==1 ? d2 : d1;
	}
	
	public static Date newYMDInstance(int year, int month, int date){
		 final Calendar cal = Calendar.getInstance();
		 cal.set(year, month-1, date,0,0,0);
		 return cal.getTime();
	}
	
	
	/***
	 *  指定时间    获取这个月内一共有多少天
	 * @param date
	 * @return
	 */
	public static int getDaysByMonth(Date date){
		Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    return cal.getActualMaximum(Calendar.DATE);
	}
	
	public static int getDaysByMonth(int year , int month){
		Calendar cal = Calendar.getInstance();
	    cal.set(Calendar.YEAR, year);
	    cal.set(Calendar.MONTH, month-1);
	    return cal.getActualMaximum(Calendar.DATE);
	}
	/**
	 * 获得两个日期之间的所有月份
	 * @param minDate
	 * @param maxDate
	 * @return
	 * @throws ParseException
	 */
	public static List<Date> getMonthBetween(Date minDate, Date maxDate) {
	    List<Date> result = new ArrayList<Date>();

	    Calendar min = Calendar.getInstance();
	    Calendar max = Calendar.getInstance();

	    min.setTime(minDate);
	    min.add(Calendar.MONTH, 1);

	    max.setTime(maxDate);
	    Calendar curr = min;
	    while (curr.before(max)) {
	     result.add(getFirstDayOfMonth(curr.getTime()));
	     curr.add(Calendar.MONTH, 1);
	    }
	    return result;
	}

	/**
	 * 获取两个日期之间的日期
	 * @param start 开始日期
	 * @param end 结束日期
	 * @return 日期集合
	 */
	public static List<Date> getDatesBetween(Date start, Date end) {
	    List<Date> result = new ArrayList<Date>();
	    Calendar tempStart = Calendar.getInstance();
	    tempStart.setTime(start);
	    tempStart.add(Calendar.DAY_OF_YEAR, 1);
	    
	    Calendar tempEnd = Calendar.getInstance();
	    tempEnd.setTime(end);
	    while (tempStart.before(tempEnd)) {
	        result.add(tempStart.getTime());
	        tempStart.add(Calendar.DAY_OF_YEAR, 1);
	    }
	    return result;
	}

	/**
	 * 获取两个日期相差月份
	 * eg：2018-05-03（date1）至2019-04-07（date2）   return -11
	 * eg: 2019-04-07（date1）至2018-05-03 （date2）  return 11
	 * 备注：不比较天数day  2018-04-29至2018-05-01 return -1
	 *
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int getDiffMonth(Date date1, Date date2){
		int yearDiff = getYear(date1)-getYear(date2);
		int monthDiff = getMonth(date1)-getMonth(date2);
		return yearDiff*12+monthDiff;
	}
}
