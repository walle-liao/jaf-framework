package com.jaf.framework.distribution.key;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

/**
 * @author: liaozhicheng.cn@163.com
 * @date: 2017-10-05
 * @since: 1.0
 */
public class MonthKeyWrapper extends KeyWrapperDecorator {

    private static final DateTimeFormatter MONTH_FORMATTER = DateTimeFormatter.ofPattern("yyyyMM");

    public MonthKeyWrapper() {
        super(new DefaultKeyWrapper());
    }

    public MonthKeyWrapper(KeyWrapper decorator) {
        super(decorator);
    }

    @Override
    protected String wrapInner(String key) {
        return YearMonth.now().format(MONTH_FORMATTER);
    }

}
