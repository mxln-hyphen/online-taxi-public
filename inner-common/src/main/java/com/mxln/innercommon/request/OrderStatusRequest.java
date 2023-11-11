package com.mxln.innercommon.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderStatusRequest {

    /**
     * 订单id
     */
    private Long id;

    /**
     * 司机去接乘客出发时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime toPickUpPassengerTime;

    /**
     * 去接乘客时，司机的经度
     */
    private String toPickUpPassengerLongitude;

    /**
     * 去接乘客时，司机的纬度
     */
    private String toPickUpPassengerLatitude;

    /**
     * 去接乘客时，司机的地点
     */
    private String toPickUpPassengerAddress;

    /**
     * 司机到达上车点时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime driverArrivedDepartureTime;

    /**
     * 接到乘客，乘客上车时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime pickUpPassengerTime;

    /**
     * 接到乘客，乘客上车经度
     */
    private String pickUpPassengerLongitude;

    /**
     * 接到乘客，乘客上车纬度
     */
    private String pickUpPassengerLatitude;

    /**
     * 乘客下车时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime passengerGetoffTime;

    /**
     * 乘客下车经度
     */
    private String passengerGetoffLongitude;

    /**
     * 乘客下车纬度
     */
    private String passengerGetoffLatitude;

}
