package com.mxln.innercommon.constant;


import lombok.Getter;


public enum CommonStatusEnum {
    /**
     * 1000~1099 验证码相关信息
     */
    VERIFICATION_SUCCESS(1000,"验证码校验成功"),
    VERIFICATION_FAIL(1010,"验证码校验失败"),
    VERIFICATION_NOT_EXIST(1011,"验证码不存在"),

    /**
     * 1100~1199 Token相关信息
     */
    TOKEN_FAIL(1199,"Token error"),

    /**
     * 1200~1299 用户信息相关
     */
    USER_NOT_EXIST(1211,"User not exist"),

    /**
     * 1300~1399 司机和车辆相关
     */
    DRIVER_NOT_EXIST(1311,"Driver not exist"),
    CAR_NOT_EXIST(1321,"Car not exist"),
    CAR_IS_BIND(1421,"Car had binded"),
    CAR_IS_NOT_BIND(1422,"car had not binded"),


    /**
     * 成功
     */
    SUCCESS(1,"success"),

    /**
     * 失败
     */
    FAIL(0,"fail")
    ;

    @Getter
    private int code;
    @Getter
    private String message;
    //private JSONObject data;


    CommonStatusEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
