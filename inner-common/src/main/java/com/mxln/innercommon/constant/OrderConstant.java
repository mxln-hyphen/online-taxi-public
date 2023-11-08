package com.mxln.innercommon.constant;

public class OrderConstant {

    //订单开始
    public static final Integer ORDER_INVALID = 0;

    //订单开始
    public static final Integer ORDER_CREATE = 1;

    //司机接单
    public static final Integer DRIVER_TAKE_ORDER = 2;

    //去接乘客
    public static final Integer DRIVER_GOTO_PASSENGER = 3;

    //司机到达乘客起点
    public static final Integer DRIVER_ARRIVAL_DEPARTURE = 4;

    //乘客上车，司机开始行程
    public static final Integer TRIP_START = 5;

    //到达目的地，行程结束，未支付
    public static final Integer ARRIVAL_DESTINATION = 6;

    //发起收款
    public static final Integer INITIATION_COLLECTION = 7;

    //支付完成
    public static final Integer PASSENGER_PAYMENT_COMPLETED = 8;

    //订单取消
    public static final Integer ORDER_CANCEL = 9;



}
