package com.mxln.innercommon.responses;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserInfoResponse {

    private Long id;

    private String passengerPhone;

    private String passengerName;

    private byte passengerGender;

    private byte State;

    private LocalDateTime gmt_create;

    private LocalDateTime gmt_modified;

    private String profilePhoto;

}
