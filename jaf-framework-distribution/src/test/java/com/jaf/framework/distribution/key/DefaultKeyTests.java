package com.jaf.framework.distribution.key;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.YearMonth;

/**
 * @author: liaozhicheng.cn@163.com
 * @date: 2017-10-05
 * @since: 1.0
 */
public class DefaultKeyTests {

    @Test
    public void keyWrapperDecoratorTest() {
        String key = "test";
        KeyWrapper keyWrapper = new DateKeyWrapper(new MonthKeyWrapper());
        String result = keyWrapper.wrap(key);
        System.out.println(result);
        Assert.assertEquals(result, LocalDate.now().toString() + "_" + YearMonth.now().toString() + "_test");
    }

}
