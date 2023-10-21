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
