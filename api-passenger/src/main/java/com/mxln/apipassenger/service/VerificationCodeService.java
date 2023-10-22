package com.mxln.apipassenger.service;

import com.mxln.apipassenger.Util.RedisUtil;
import com.mxln.apipassenger.remote.ServicePassengerUserClient;
import com.mxln.apipassenger.remote.ServiceVerificationCodeClient;
import com.mxln.innercommon.Util.JwtUtil;
import com.mxln.innercommon.constant.CommonStatusEnum;
import com.mxln.innercommon.constant.IdentityEnum;
import com.mxln.innercommon.constant.JWTTypeConstant;
import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.request.JWTDTO;
import com.mxln.innercommon.request.VerificationCodeDTO;
import com.mxln.innercommon.responses.NumberCodeResponse;
import com.mxln.innercommon.responses.TokenResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
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
        RedisUtil redisUtil = new RedisUtil(redisTemplate);
        redisUtil.insertRedis(redisUtil.generatePassengerPhoneCode(pessengerPhoneNumber)
                , verificationCode + "", 2, TimeUnit.MINUTES);

        return ResponseResult.success();
    }

    /**
     * 通过手机号在redis中获取验证码，并校验验证码是否正确
     *
     * @param passengerPhone
     * @param verificationCode
     * @return
     */
    public ResponseResult check(String passengerPhone, String verificationCode) {
        //获取验证码
        RedisUtil redisUtil = new RedisUtil(redisTemplate);
        String key = redisUtil.generatePassengerPhoneCode(passengerPhone);
        String redisCode = redisUtil.get(key);

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
        verificationCodeDTO.setPassengerPhone(passengerPhone);

        //生成双token
        JwtUtil jwtUtil = new JwtUtil();
        String accessToken = jwtUtil.generateJWT(new JWTDTO(IdentityEnum.PASSENGER.getIdentity(), passengerPhone)
                , JWTTypeConstant.ACCESSTOKEN);
        String refreshToken = jwtUtil.generateJWT(new JWTDTO(IdentityEnum.PASSENGER.getIdentity(), passengerPhone)
                , JWTTypeConstant.REFRESHTOKEN);


        //将生成的双token放入redis
        redisUtil.insertRedis(redisUtil
                        .generateUser(passengerPhone, IdentityEnum.PASSENGER.getIdentity(), JWTTypeConstant.ACCESSTOKEN)
                , accessToken, 10, TimeUnit.SECONDS);
        redisUtil.insertRedis(redisUtil
                        .generateUser(passengerPhone, IdentityEnum.PASSENGER.getIdentity(), JWTTypeConstant.REFRESHTOKEN)
                , refreshToken, 60, TimeUnit.SECONDS);


        //返回
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken(accessToken);
        tokenResponse.setRefreshToken(refreshToken);

        return ResponseResult.success(tokenResponse);
    }
}
