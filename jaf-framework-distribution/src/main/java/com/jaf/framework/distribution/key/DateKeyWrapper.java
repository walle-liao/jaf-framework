package com.jaf.framework.distribution.key;

import java.time.LocalDate;

/**
 * @author: liaozhicheng
 * @Timestamp: 2017/07/19 11:04
 */
public class DateKeyWrapper extends KeyWrapperDecorator {

    public DateKeyWrapper(){
        this(new DefaultKeyWrapper());
    }

    public DateKeyWrapper(KeyWrapper keyWrapper) {
        super(keyWrapper);
    }

    @Override
    protected String wrapInner(String key) {
        return LocalDate.now().toString();
    }

}
