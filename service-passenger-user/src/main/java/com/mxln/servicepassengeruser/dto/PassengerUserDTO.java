package com.mxln.servicepassengeruser.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("user")
public class PassengerUserDTO {

    private Long id;

    private String passengerPhone;

    private String passengerName;

    private byte passengerGender;

    private byte State;

    private LocalDateTime gmt_create;

    private LocalDateTime gmt_modified;
}
