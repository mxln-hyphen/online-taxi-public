package com.mxln.innercommon.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tbl_price_rule")
public class PriceRuleDTO {

    private String cityCode;

    private String vehicleType;

    private Integer startMile;

    private Double startFare;

    private Double unitPricePerMile;

    private Double unitPricePerMinute;

}
