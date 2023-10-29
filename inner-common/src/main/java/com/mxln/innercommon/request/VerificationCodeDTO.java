package com.mxln.innercommon.request;

import lombok.Data;

@Data
public class VerificationCodeDTO {
    //乘客手机号
    private String passengerPhone;

    //司机手机号
    private String driverPhone;

    //验证码
    private String verificationCode;




}
