package com.mxln.innercommon.constant;

import lombok.Data;

public class DriverCarConstant {

    /**
     * 绑定
     */
    //车辆与司机已绑定
    public static Integer CAR_DRIVER_IS_BIND = 1;

    //车辆与司机未绑定
    public static Integer CAR_DRIVER_IS_NOT_BIND = 0;

    /**
     * 司机工作状态
     */
    //工作状态数量
    public static Integer DRIVER_WORK_STATUS_KIND = 3;

    //工作状态：收车
    public static Byte DRIVER_WORK_STATUS_RELEX = 0;

    //工作状态：接单
    public static Byte DRIVER_WORK_STATUS_ORDER = 1;

    //工作状态：暂停接单
    public static Byte DRIVER_WORK_STATUS_PAUSE_ORDER = 2;


}
