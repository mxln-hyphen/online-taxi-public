package com.mxln.innercommon.dto;

import lombok.Data;

@Data
/**
 * 请求路径预测价格DTO
 */
public class ForecastPriceDTO {

    private String depLongitude;

    private String depLatitude;

    private String destLongitude;

    private String destLatitude;
}
