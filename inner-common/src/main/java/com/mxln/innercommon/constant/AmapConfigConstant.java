package com.mxln.innercommon.constant;

/**
 * 高德地图API常量类
 */
public class AmapConfigConstant {

    //密钥
    public static String KEY_VALUE = "5cb5123bb90ba4bbacdaaa4c1c69bbb0";

    //请求路径距离和时长URL前缀
    public static String URL_PREFIX = "https://restapi.amap.com/v3/direction/driving";

    //请求路径距离和时长URL起始地参数名
    public static String URL_ORIGIN_PARAMETERS = "origin";

    //请求路径距离和时长URL目的定参数名
    public static String URL_DESTINATION_PARAMETERS = "destination";

    //请求路径距离和时长URL密钥参数名
    public static String URL_KEY_PARAMETERS = "key";

    //请求路径距离和时长JSON状态参数名
    public static String JSON_STATUS_PARAMETERS = "status";

    //请求区域信息URL前缀
    public static String DISTRICT_URL_PREFIX="https://restapi.amap.com/v3/config/district";

    //请求区域信息URL最高行政区名参数名
    public static String DISTRICT_URL_KEYWORDS_PARAMETERS = "keywords";

    /**请求区域信息URL显示下级行政区数参数名，可选值：0、1、2、3
     * 0：不返回下级行政区；
     * 1：返回下一级行政区；
     * 2：返回下两级行政区；
     * 3：返回下三级行政区；
     */
    public static String DISTRICT_URL_SUBDISTRICT_PARAMETERS = "subdistrict";

    //请求区域信息URL密钥参数名
    public static String DISTRICT_URL_KEY_PARAMETERS = "key";



}
