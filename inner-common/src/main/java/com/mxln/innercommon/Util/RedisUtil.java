package com.mxln.innercommon.Util;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

public class RedisUtil {
    private StringRedisTemplate redisTemplate;

    //redis前缀
    private final String PassengerVerificationCodePrefix = "user-verification-code-";
    private final String DriverVerificationCodePrefix = "driver-verification-code-";
    private final String JWTPrefix = "jwt-";


    public RedisUtil(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String generatePassengerPhoneCode(String passengerPhoneNumber) {
        return "" + PassengerVerificationCodePrefix + passengerPhoneNumber;
    }

    public String generateUserJWT(String passengerPhone, int identity, String type) {
        return "" + JWTPrefix + passengerPhone + "-" + identity + "-" + type;
    }

    public String generateDriverPhoneCode(String driverPhoneNumber) {
        return "" + DriverVerificationCodePrefix + driverPhoneNumber;
    }

    /**
     * 将键值对存入redis
     *
     * @param key
     * @param value
     * @param ttl
     * @param timeUnit
     */
    public void insertRedis(String key, String value, int ttl, TimeUnit timeUnit) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set(key, value, ttl, timeUnit);
    }

    /**
     * 通过key从redis中获取value
     *
     * @param key
     * @return
     */
    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
