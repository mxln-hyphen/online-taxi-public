package com.mxln.innercommon.request;

import lombok.Data;

@Data
public class PassengerInfoPushRequest {


    /**
     * 乘客ID
     */
    private Long passengerId;

    /**
     * 乘客手机号
     */
    private String passengerPhone;

    /**
     * 预计出发地点详细地址
     */
    private String departure;

    /**
     * 预计出发地点经度
     */
    private String depLongitude;

    /**
     * 预计出发地点纬度
     */
    private String depLatitude;

    /**
     * 预计目的地
     */
    private String destination;

    /**
     * 预计目的地经度
     */
    private String destLongitude;

    /**
     * 预计目的地纬度
     */
    private String destLatitude;
}
