package com.mxln.innercommon.request;

import lombok.Data;

@Data
public class ForecastPriceRequest {

    private String depLongitude;

    private String depLatitude;

    private String destLongitude;

    private String destLatitude;
}
