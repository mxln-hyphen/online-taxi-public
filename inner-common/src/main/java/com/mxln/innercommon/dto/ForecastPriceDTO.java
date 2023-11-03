package com.mxln.innercommon.dto;

import lombok.Data;

@Data
/**
 * 请求路径预测价格DTO
 */
public class ForecastPriceDTO {

    //城市代码
    private String cityCode;

    //车型
    private String vehicleType;

    private String depLongitude;

    private String depLatitude;

    private String destLongitude;

    private String destLatitude;
}
