package com.jaf.framework.components.utils.date;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.Objects;

/**
 * value object
 */
public final class Month implements Serializable {

	private static final long serialVersionUID = 194869417028849427L;
	
	// 日期，永远为月份的第一天
    private Date value;

    private Month(Date value) {
        this.value = value;
    }

    public static Month now() {
    	return Month.from(new Date());
    }
    
    public static Month of(int year, int month) {
        return Month.of(YearMonth.of(year, month));
    }

    public static Month of(YearMonth yearMonth) {
        Objects.requireNonNull(yearMonth);
        return new Month(LocalDateUtils.toDate(yearMonth.atDay(1)));
    }

    public static Month from(Date date) {
        Objects.requireNonNull(date);
        return Month.of(DateUtils.toYearMonth(date));  // date 重置到当月第一天
    }

    public static Month parse(String monthStr) {
        return Month.of(YearMonth.parse(monthStr));
    }

    public YearMonth toYearMonth() {
        return DateUtils.toYearMonth(this.value);
    }

    public LocalDate toLocalDate() {
        return DateUtils.toLocalDate(this.value);
    }

    public Month plusMonths(int monthToAdd) {
        return Month.of(this.toYearMonth().plusMonths(monthToAdd));
    }

    public Date toDate() {
        return this.value;
    }

    public Date atEndDay() {
        return LocalDateUtils.toDate(this.toYearMonth().atEndOfMonth());
    }

    public int getYearValue() {
        return toLocalDate().getYear();
    }

    public int getMonthValue() {
        return toLocalDate().getMonthValue();
    }

    public int getDayOfMonth() {
        return toLocalDate().with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();
    }

    public String getMonthString() {
        return this.toString();
    }

    public String toString() {
        return toYearMonth().toString();
    }

    public String toDateString() {
        return DateUtils.formatYMD(this.value);
    }

    public String toFormatString(String formatter) {
        DateFormat df = new SimpleDateFormat(formatter);
        return df.format(this.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Month month = (Month) o;
        return this.toYearMonth().equals(month.toYearMonth());
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    public static void main(String[] args) {
        Month month = Month.parse("2017-02");
        System.out.println(month);
        System.out.println(month.toDateString());
        System.out.println(DateUtils.formatYMD(month.atEndDay()));
        System.out.println(DateUtils.formatYMD(month.plusMonths(-1).atEndDay()));
        System.out.println(month.getDayOfMonth());
    }

}
