package com.mxln.innercommon.constant;

import lombok.Getter;

public enum IdentityEnum {
    //司机
    DRIVER(2000),
    //乘客
    PASSENGER(1000),
    //管理员
    ADMIN(0);

    @Getter
    private int identity;

    IdentityEnum(int identity) {
        this.identity = identity;
    }
}
