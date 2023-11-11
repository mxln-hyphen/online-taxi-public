package com.mxln.innercommon.Util;

public class SsePrefixUtils {

    private static String CONNECT_SIGN = "$";

    public static String generatorSseKey(String userId, String identity) {

        return userId + CONNECT_SIGN + identity;

    }
}
