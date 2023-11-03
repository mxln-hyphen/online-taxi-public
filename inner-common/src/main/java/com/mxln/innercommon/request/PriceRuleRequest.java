package com.mxln.innercommon.request;

import lombok.Data;

@Data
public class PriceRuleRequest {

    private String cityCode;

    private String vehicleType;

    private Integer startMile;

    private Double startFare;

    private Double unitPricePerMile;

    private Double unitPricePerMinute;
}
