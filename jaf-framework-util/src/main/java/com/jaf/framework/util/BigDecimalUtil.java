package com.jaf.framework.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * bigdecimal 处理类
 */
public class BigDecimalUtil {

    public static final BigDecimal HUNDRED = new BigDecimal(100);
    public static final BigDecimal THOUSAND = new BigDecimal(1000);
    public static final BigDecimal TEN_THOUSAND = new BigDecimal(10000);
    public static final BigDecimal NEGATIVE_FACTOR = new BigDecimal(-1);

    public static final int DEFAULT_ROUNDING_MODE = BigDecimal.ROUND_HALF_UP;

    /**
     * 加
     *
     * @param one
     * @param two
     * @return
     */
    public static BigDecimal add(BigDecimal one, BigDecimal two) {
        return add(one, two, null, null);
    }

    /**
     * 加
     *
     * @param one
     * @param two
     * @param scale 保留小数位
     * @return
     */
    public static BigDecimal add(BigDecimal one, BigDecimal two, Integer scale) {
        return add(one, two, scale, null);
    }

    /**
     * 加
     *
     * @param one
     * @param two
     * @param scale 保留小数位
     * @return
     */
    public static BigDecimal add(BigDecimal one, BigDecimal two, Integer scale, Integer roundingMode) {
        return calculate(one, two, 1, scale, roundingMode);
    }

    /**
     * 减
     *
     * @param one
     * @param two
     * @return
     */
    public static BigDecimal subtract(BigDecimal one, BigDecimal two) {
        return subtract(one, two, null, null);
    }

    /**
     * 减
     *
     * @param one
     * @param two
     * @param scale 保留小数位
     * @return
     */
    public static BigDecimal subtract(BigDecimal one, BigDecimal two, Integer scale) {
        return subtract(one, two, scale, null);
    }

    /**
     * 减
     *
     * @param one
     * @param two
     * @param scale 保留小数位
     * @return
     */
    public static BigDecimal subtract(BigDecimal one, BigDecimal two, Integer scale, Integer roundingMode) {
        return calculate(one, two, 2, scale, roundingMode);
    }

    /**
     * 乘
     *
     * @param one
     * @param two
     * @return
     */
    public static BigDecimal multiply(BigDecimal one, BigDecimal two) {
        return multiply(one, two, null, null);
    }

    /**
     * 乘
     *
     * @param one
     * @param two
     * @param scale 保留小数位
     * @return
     */
    public static BigDecimal multiply(BigDecimal one, BigDecimal two, Integer scale) {
        return multiply(one, two, scale, null);
    }

    /**
     * 乘
     *
     * @param one
     * @param two
     * @param scale 保留小数位
     * @return
     */
    public static BigDecimal multiply(BigDecimal one, BigDecimal two, Integer scale, Integer roundingMode) {
        return calculate(one, two, 3, scale, roundingMode);
    }

    /**
     * 除
     *
     * @param one
     * @param two
     * @return
     */
    public static BigDecimal divide(BigDecimal one, BigDecimal two) {
        return divide(one, two, 8, null);
    }

    /**
     * 除
     *
     * @param one
     * @param two
     * @param scale 保留小数位
     * @return
     */
    public static BigDecimal divide(BigDecimal one, BigDecimal two, Integer scale) {
        return divide(one, two, scale, null);
    }

    /**
     * 除
     *
     * @param one
     * @param two
     * @param scale 保留小数位
     * @return
     */
    public static BigDecimal divide(BigDecimal one, BigDecimal two, Integer scale, Integer roundingMode) {
        return calculate(one, two, 4, scale, roundingMode);
    }

    /**
     * 取负数
     *
     * @param number
     * @return 一定是一个负数
     */
    public static BigDecimal negative(BigDecimal number) {
        if (compareTo(BigDecimal.ZERO, number) >= 0)
            return number;

        return multiply(NEGATIVE_FACTOR, number);
    }

    private static BigDecimal calculate(BigDecimal one, BigDecimal two, int action, Integer scale, Integer roundingMode) {
        if (roundingMode == null) {
            roundingMode = DEFAULT_ROUNDING_MODE;
        }
        if (one == null) {
            one = BigDecimal.ZERO;
        }
        if (two == null) {
            two = BigDecimal.ZERO;
        }

        BigDecimal result = BigDecimal.ZERO;
        final Context context = new Context();
        context.setAction(action);
        context.setOne(one);
        context.setTwo(two);
        context.setResult(result);

        one = context.getOne();
        two = context.getTwo();
        result = context.getResult();
        action = context.getAction();

        result = result.add(one);
        switch (action) {
            case 1:
                result = result.add(two);
                break;
            case 2:
                result = result.subtract(two);
                break;
            case 3:
                if (compareTo(one, null) == 0 || compareTo(two, null) == 0) {
                    result = BigDecimal.ZERO;
                    break;
                }
                result = result.multiply(two);
                break;
            case 4:
                if (compareTo(one, null) == 0 || compareTo(two, null) == 0) {
                    result = BigDecimal.ZERO;
                    break;
                }
                if (scale != null && roundingMode != null) {
                    result = result.divide(two, scale, roundingMode);
                } else if (scale != null) {
                    result = result.divide(two, scale);
                } else {
                    result = result.divide(two);
                }
                break;
            default:
                throw new RuntimeException("[action=" + action + "]必须是[1.加,2.减,3.乘,4.除]");
        }
        switch (action) {
            case 1:
            case 2:
            case 3:
                if (scale != null && roundingMode != null) {
                    result = result.setScale(scale, roundingMode);
                } else if (scale != null) {
                    result = result.setScale(scale);
                }
        }
        context.setResult(result);
        return context.getResult();
    }

    public static BigDecimal max(BigDecimal... args) {
        Objects.requireNonNull(args);
        if (args.length == 1)
            return args[0];

        return Stream.of(args).max(BigDecimalUtil::compareTo).get();
    }

    public static BigDecimal min(BigDecimal... args) {
        Objects.requireNonNull(args);
        if (args.length == 1)
            return args[0];

        return Stream.of(args).min(BigDecimalUtil::compareTo).get();
    }

    public static double doubleValue(BigDecimal value) {
        if (value == null) {
            return 0;
        }
        return value.doubleValue();
    }

    public static String toString(BigDecimal value) {
        if (value == null) {
            return "";
        }
        return value.toString();
    }

    public static String toString(BigDecimal value, int setPrecision) {
        if (value == null) {
            return "";
        }
        return value.setScale(setPrecision, BigDecimal.ROUND_HALF_UP).toString();
    }

    public static double doubleValue(BigDecimal bigDecimal, Double defaultValue) {
        if (bigDecimal == null) {
            return defaultValue;
        }
        return bigDecimal.doubleValue();
    }

    public static boolean isEmpty(BigDecimal bigDecimal) {
        return bigDecimal == null || bigDecimal.compareTo(BigDecimal.ZERO) == 0;
    }

    public static int compareTo(BigDecimal one, BigDecimal two, double delta) {
        if (delta != 0) {
            final BigDecimal v = BigDecimalUtil.subtract(one, two, 5);
            if (v.abs().doubleValue() <= Math.abs(delta)) {
                return 0;
            }
        }
        return BigDecimalUtil.compareTo(one, two);
    }

    public static int compareTo(BigDecimal one, BigDecimal two) {
        one = isEmpty(one) ? BigDecimal.ZERO : one;
        two = isEmpty(two) ? BigDecimal.ZERO : two;
        return one.compareTo(two);
    }

    public static BigDecimal newInstance(String value) {
        if (value == null || value.trim().length() == 0) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(value);
    }

    public static BigDecimal newInstance(Double value) {
        if (value == null) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(value);
    }

    public static BigDecimal newInstance(Integer value) {
        if (value == null) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(value);
    }

    /**
     * 金额转换成万元
     *
     * @return
     */
    public static String convertWAN(BigDecimal amount, int scale) {
        String ret = "0";
        if (amount == null || amount.intValue() == 0) {
            return ret;
        }
        ret = amount.divide(TEN_THOUSAND).setScale(scale, RoundingMode.HALF_UP).toString();

        if (ret.indexOf(".") > 0) {
            ret = ret.replaceAll("0+?$", ""); // 去掉后面无用的零
            ret = ret.replaceAll("[.]$", ""); // 如小数点后面全是零则去掉小数点
        }
        return ret;
    }

    public static String convertWAN(BigDecimal amount) {
        return convertWAN(amount, 2);
    }

    public static BigDecimal convertToWan(BigDecimal amount) {
        return new BigDecimal(convertWAN(amount, 2));
    }

    public static class Context {
        private BigDecimal one;
        private BigDecimal two;
        private BigDecimal result;
        private int action;
        private MathContext mathContext;

        public BigDecimal getOne() {
            return one;
        }

        public void setOne(BigDecimal one) {
            this.one = one;
        }

        public BigDecimal getTwo() {
            return two;
        }

        public void setTwo(BigDecimal two) {
            this.two = two;
        }

        public BigDecimal getResult() {
            return result;
        }

        public void setResult(BigDecimal result) {
            this.result = result;
        }

        public int getAction() {
            return action;
        }

        public void setAction(int action) {
            this.action = action;
        }

        public MathContext getMathContext() {
            return mathContext;
        }

        public void setMathContext(MathContext mathContext) {
            this.mathContext = mathContext;
        }
    }

    public static void main(String[] args) {
        BigDecimal b1 = new BigDecimal(1);
        BigDecimal b2 = new BigDecimal(2);
        BigDecimal b3 = new BigDecimal(3);
        System.out.println(max(b1, b3, b2, BigDecimalUtil.HUNDRED, BigDecimalUtil.TEN_THOUSAND));
        System.out.println(min(b1, b2, BigDecimal.ZERO));
    }
}
