package com.jaf.framework.components.utils.common;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

/**
 * @author: liaozhicheng
 * @date: 2019-03-06
 * @since: 1.0
 */
public class DozerMapper {

    private static class DozerMapperHolder {
        private static final Mapper single = new DozerBeanMapper();
    }

    public static Mapper get() {
        return DozerMapperHolder.single;
    }

}
