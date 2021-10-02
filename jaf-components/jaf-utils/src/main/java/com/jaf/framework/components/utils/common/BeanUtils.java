package com.jaf.framework.components.utils.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

public class BeanUtils {

    public static <E, D> Function<E, D> copyFun(Class<D> dClass) {
        return e -> DozerMapper.get().map(e, dClass);
    }

    public static <E, D> D copy(E e, Class<D> dClass) {
        if (e == null) {
            return null;
        }
        return copyFun(dClass).apply(e);
    }

    public static <E, D> void copy(E e, D d) {
        if (e == null) {
            return;
        }
        DozerMapper.get().map(e, d);
    }

    public static <E, D> List<D> copyList(Collection<E> eCollection, Class<D> dClass) {
        if (CollectionUtils.isEmpty(eCollection)) {
            return new ArrayList<>();
        }
        return eCollection.stream().map(copyFun(dClass)).collect(toList());
    }
}
