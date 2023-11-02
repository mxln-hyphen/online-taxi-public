package com.mxln.innercommon.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("driver_work_status")
public class DriverWorkStatusDTO {

    private Long driverId;

    private Byte workStatus;

    private LocalDateTime gmt_create;

    private LocalDateTime gmt_modified;


}
