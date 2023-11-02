package com.mxln.innercommon.request;

import lombok.Data;

@Data
public class DriverWorkStatusRequest {

    private Long driverId;

    private Integer workStatus;

}
