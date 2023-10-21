package com.mxln.apipassenger.service;

import com.mxln.apipassenger.remote.ServicePassengerUserClient;
import com.mxln.apipassenger.remote.ServiceVerificationCodeClient;
import com.mxln.innercommon.constant.CommonStatusEnum;
import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.request.VerificationCodeDTO;
import com.mxln.innercommon.responses.NumberCodeResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class VerificationCodeService {

    @Autowired
    private ServiceVerificationCodeClient serviceVerificationcodeClient;

    @Autowired
    private ServicePassengerUserClient servicePassengerUserClient;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private String verificationCodePrefix = "user-verification-code-";

    /**
     * 调用验证码服务并存入redis
     *
     * @return
     */
    public ResponseResult get(String pessengerPhoneNumber) {
        //调用验证码服务,获取验证码
        ResponseResult<NumberCodeResponse> numberCodeResponse = serviceVerificationcodeClient.getNumberCode(6);
        String verificationCode = numberCodeResponse.getData().getNumberCode();
        System.out.println(verificationCode);

        //验证码存入redis
        //key
        String key = this.generatePassengerPhoneCode(pessengerPhoneNumber);
        //value
        String value = verificationCode + "";
        //timeout
        int ttl = 2;
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set(key, value, ttl, TimeUnit.MINUTES);

        return ResponseResult.success();
    }

    /**
     * 通过手机号在redis中获取验证码，并校验验证码是否正确
     *
     * @param passengerPhoneNumber
     * @param verificationCode
     * @return
     */
    public ResponseResult check(String passengerPhoneNumber, String verificationCode) {
        //获取验证码
        String key = generatePassengerPhoneCode(passengerPhoneNumber);
        String redisCode = redisTemplate.opsForValue().get(key);

        //校验验证码是否正确
        if (StringUtils.isBlank(redisCode)) {//验证码为空
            return new ResponseResult().setCode(CommonStatusEnum.VERIFICATION_NOT_EXIST.getCode())
                    .setMessage(CommonStatusEnum.VERIFICATION_NOT_EXIST.getMessage());
        } else if (!verificationCode.trim().equals(redisCode)) {//验证码错误
            return new ResponseResult().setCode(CommonStatusEnum.VERIFICATION_FAIL.getCode())
                    .setMessage(CommonStatusEnum.VERIFICATION_FAIL.getMessage());
        }

        //验证码校验成功
        //调用服务，登陆或注册用户
        VerificationCodeDTO verificationCodeDTO = new VerificationCodeDTO();
        verificationCodeDTO.setPassengerPhone(passengerPhoneNumber);
        servicePassengerUserClient.loginOrRegister(verificationCodeDTO);

        return new ResponseResult().setCode(CommonStatusEnum.VERIFICATION_SUCCESS.getCode())
                .setMessage(CommonStatusEnum.VERIFICATION_SUCCESS.getMessage());
    }

    private String generatePassengerPhoneCode(String passengerPhoneNumber) {
        return "" + verificationCodePrefix + passengerPhoneNumber;
    }
}
