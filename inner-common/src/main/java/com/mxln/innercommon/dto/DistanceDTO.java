package com.mxln.innercommon.dto;

import lombok.Data;

@Data
/**
 * 请求路径距离和时长DTO
 */
public class DistanceDTO {

    private String depLongitude;

    private String depLatitude;

    private String destLongitude;

    private String destLatitude;

}
