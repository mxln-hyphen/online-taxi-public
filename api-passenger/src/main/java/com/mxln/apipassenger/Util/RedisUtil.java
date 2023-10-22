package com.mxln.apipassenger.Util;

import com.mxln.innercommon.constant.IdentityEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

public class RedisUtil {
    private StringRedisTemplate redisTemplate;

    //redis前缀
    private final String verificationCodePrefix = "user-verification-code-";
    private final String JWTPrefix = "jwt-";


    public RedisUtil(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String generatePassengerPhoneCode(String passengerPhoneNumber) {
        return "" + verificationCodePrefix + passengerPhoneNumber;
    }

    public String generateUser(String passengerPhone, int identity, String type) {
        return "" + JWTPrefix + passengerPhone + "-" + identity + "-" + type;
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
