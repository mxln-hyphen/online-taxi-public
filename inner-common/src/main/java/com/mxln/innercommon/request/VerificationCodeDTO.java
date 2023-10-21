package com.mxln.innercommon.request;

import lombok.Data;

@Data
public class VerificationCodeDTO {
    //手机号
    private String passengerPhone;

    //验证码
    private String verificationCode;


}
