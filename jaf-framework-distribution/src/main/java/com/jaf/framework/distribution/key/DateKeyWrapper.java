package com.jaf.framework.distribution.key;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author: liaozhicheng
 * @Timestamp: 2017/07/19 11:04
 */
public class DateKeyWrapper extends KeyWrapperDecorator {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    public DateKeyWrapper(){
        this(new DefaultKeyWrapper());
    }

    public DateKeyWrapper(KeyWrapper keyWrapper) {
        super(keyWrapper);
    }

    @Override
    protected String wrapInner(String key) {
        return LocalDate.now().format(DATE_TIME_FORMATTER);
    }

}
