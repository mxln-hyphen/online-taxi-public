package com.mxln.innercommon.Util;

import java.math.BigDecimal;

/**
 * BigDecimal工具类
 */
public class BigDecimalUtil {

    //加
    public static BigDecimal add(double v1,double v2){
        return BigDecimal.valueOf(v1).add(BigDecimal.valueOf(v2));
    }

    //减
    public static BigDecimal sub(double v1,double v2){
        return BigDecimal.valueOf(v1).subtract(BigDecimal.valueOf(v2));
    }

    //乘
    public static BigDecimal mul(double v1,double v2){
        return BigDecimal.valueOf(v1).multiply(BigDecimal.valueOf(v2));
    }

    //除
    public static BigDecimal div(double v1,double v2){
        return BigDecimal.valueOf(v1).divide(BigDecimal.valueOf(v2),BigDecimal.ROUND_CEILING);
    }
}
