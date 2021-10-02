package com.jaf.framework.components.utils.date;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.Objects;

/**
 * @author: liaozhicheng
 */
public final class LocalDateUtils {

    // ============ instance ============
    public static LocalDate now() {
        return LocalDate.now();
    }

    public static LocalDate of(int year, int month, int dayOfMonth) {
        return LocalDate.of(year, month, dayOfMonth);
    }

    /**
     * 传入的日期格式必须是：yyyy-MM-dd 模式
     * @param text
     * @return
     */
    public static LocalDate parse(String text) {
        return LocalDate.parse(text, DateTimeFormatter.ISO_LOCAL_DATE);
    }

    // ============ convert date type ============
    public static Date toDate(LocalDate localDate) {
        Objects.requireNonNull(localDate);
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDateTime toLocalDateTime(LocalDate localDate, int hour, int minute, int second) {
        Objects.requireNonNull(localDate);
        return localDate.atTime(hour, minute, second);
    }

    public static YearMonth toYearMonth(LocalDate localDate) {
        Objects.requireNonNull(localDate);
        return YearMonth.from(localDate);
    }
    
    public static LocalDate toLocalDate(Date date){
    	return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).toLocalDate();
    }

    // ============ calculate ============
    public static LocalDate lastDayOfMonth(LocalDate localDate) {
        Objects.requireNonNull(localDate);
        return localDate.with(TemporalAdjusters.lastDayOfMonth());
    }

    public static LocalDate firstDayOfMonth(LocalDate localDate) {
        Objects.requireNonNull(localDate);
        return localDate.with(TemporalAdjusters.firstDayOfMonth());
    }

    public static LocalDate firstDayOfNextMonth(LocalDate localDate) {
        Objects.requireNonNull(localDate);
        return localDate.with(TemporalAdjusters.firstDayOfNextMonth());
    }

    public static LocalDate firstDayOfNextYear(LocalDate localDate) {
        Objects.requireNonNull(localDate);
        return localDate.with(TemporalAdjusters.firstDayOfNextYear());
    }

    public static long getMonths(LocalDate beginLocalDate, LocalDate endLocalDate) {
        Objects.requireNonNull(beginLocalDate, "beginLocalDate");
        Objects.requireNonNull(endLocalDate, "endLocalDate");
        return Period.between(beginLocalDate, endLocalDate).toTotalMonths();
    }

    public static long getDays(LocalDate beginLocalDate, LocalDate endLocalDate) {
        Objects.requireNonNull(beginLocalDate, "beginLocalDate");
        Objects.requireNonNull(endLocalDate, "endLocalDate");
        LocalDateTime beginLocalDateTime = toLocalDateTime(beginLocalDate, 0, 0, 0);
        LocalDateTime endLocalDateTime = toLocalDateTime(endLocalDate, 0, 0, 0);
        return Duration.between(beginLocalDateTime, endLocalDateTime).toDays();
    }

    public static void main(String[] args) {
        LocalDate date = LocalDate.of(2016, 1, 31);
//        System.out.println(lastDayOfMonth(date).toString());
//        System.out.println(DateUtil.formatYMD(toDate(date)));
//        System.out.println(toLocalDate(new Date()));
//        System.out.println(parse("2015-10-11"));

        System.out.println(getMonths(LocalDate.now(), date));
        System.out.println(getDays(date, LocalDate.now()));

        // 2016-1-31, 2017-2-9
//        LocalDate now = LocalDate.now();
//        System.out.println(now.until(date).getDays());
//        Duration d = Duration.between(date, now);
//        System.out.println(d.toDays());
//
//        Period p = Period.between(date, now);
//        System.out.println(p.getDays());
//        System.out.println(p.getMonths());
//        System.out.println(p);
//        System.out.println(p.getYears());
//        System.out.println(p.getDays());
//        System.out.println(p.toTotalMonths());
//
//
//        LocalDateTime nowt = now.atTime(0, 0, 0);
//        LocalDateTime datet = date.atTime(0, 0, 0);
//        Duration d = Duration.between(datet, nowt);
//        System.out.println(d.toDays());
//        System.out.println(d.get(ChronoUnit.MONTHS));
    }

}
