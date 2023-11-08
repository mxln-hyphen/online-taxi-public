package com.mxln.innercommon.responses;

import lombok.Data;

@Data
public class DriverWorkInfoResponse {

    //司机id
    private Long driverId;

    //司机手机号
    private String driverPhone;

    //司机工作状态
    private int driverWorkState;

    //司机机动车驾驶证号
    private String licenseId;

    //司机车号牌
    private String vehicleNo;

    //车辆id
    private String carId;
}
