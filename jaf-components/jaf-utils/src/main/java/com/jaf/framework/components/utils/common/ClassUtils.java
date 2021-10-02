package com.jaf.framework.components.utils.common;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class ClassUtils {
    /**
     * 得到cls的一个字段
     *
     * @param cls
     * @param fieldName
     * @return
     * @throws Exception
     * @throws SecurityException
     */
    public static Field getField(Class<?> cls, String fieldName) {
        if (cls == Object.class)
            return null;
        Field f = null;
        try {
            f = cls.getDeclaredField(fieldName);
        } catch (Exception e) {

        }
        if (null == f) {
            return getField(cls.getSuperclass(), fieldName);
        } else {
            f.setAccessible(true);
            return f;
        }
    }

    /**
     * 得到fieldName 的值
     *
     * @param obj
     * @param fieldName
     * @return
     */
    public static Object getFieldValue(Object obj, String fieldName) {
        if (obj instanceof Map<?, ?>) {
            return ((Map<?, ?>) obj).get(fieldName);
        }
        if (obj == null) {
            return null;
        }
        Field f = getField(obj.getClass(), fieldName);
        if (null == f) {
            return null;
        }
        try {
            return f.get(obj);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 得到fieldName 的值
     *
     * @param obj
     * @param fieldName
     * @return
     */
    public static Object getFieldValue1(Object obj, String fieldName) {
        if (StringUtils.isEmpty(fieldName)) {
            throw new NullPointerException("字段名不能为空");
        }
        final String[] fs = fieldName.split("\\.");

        Object v = obj;
        for (String name : fs) {
            v = getFieldValue(v, name);
        }
        return v;
    }

    /**
     * 复制类属性，该方法只实现本类中属性拷贝，如需拷贝父类属性 可采用递归的方式
     *
     * @param <T>
     * @param clazz
     * @param source
     * @param target
     * @author hgpeng
     */
    public static <T> void copyPropertyToOtherClass(Class<T> clazz, Object source, Object target) {
        if (source == null || target == null) {
            throw new RuntimeException("源对象与目标对象都不能为空");
        }
        Field[] fields = clazz.getDeclaredFields();
        for (Field eachField : fields) {
            String fieldName = eachField.getName();
            // serialVersionUID没有对应的set方法
            if (fieldName.equals("serialVersionUID")) {
                continue;
            }
            String str = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            String setMethodName = "set" + str;
            String getMethodName = "get" + str;
            try {
                Method setMethod = clazz.getMethod(setMethodName, new Class[]{eachField.getType()});
                Method getMethod = clazz.getMethod(getMethodName, new Class[]{});
                // 获取源类属性值
                Object o = getMethod.invoke(source, new Object[]{});
                if (o != null) {
                    setMethod.invoke(target, new Object[]{o});
                }
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

    }

    public static Object newInstance(String clsName) {
        try {
            Class<?> cls = Class.forName(clsName);
            return cls.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 得到fieldName 的值
     *
     * @param obj
     * @param fieldName
     * @return
     */
    public static void setFieldValue(Object obj, String fieldName, Object value) {
        Field f = getField(obj.getClass(), fieldName);
        try {
            f.set(obj, value);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
