package com.mxln.apidriver.service;

import com.mxln.apidriver.remote.ServiceDriverUserClient;
import com.mxln.apidriver.remote.ServiceVerificationCodeClient;
import com.mxln.innercommon.Util.JwtUtil;
import com.mxln.innercommon.Util.RedisUtil;
import com.mxln.innercommon.constant.CommonStatusEnum;
import com.mxln.innercommon.constant.IdentityEnum;
import com.mxln.innercommon.constant.JWTTypeConstant;
import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.request.JWTDTO;
import com.mxln.innercommon.request.VerificationCodeDTO;
import com.mxln.innercommon.responses.NumberCodeResponse;
import com.mxln.innercommon.responses.TokenResponse;
import com.mxln.innercommon.responses.VerificationCodeResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class VerificationService {

    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;

    @Autowired
    private ServiceVerificationCodeClient serviceVerificationCodeClient;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 根据手机号获取验证码
     * @param verificationCodeDTO
     * @return
     */
    public ResponseResult getVerificationCode(VerificationCodeDTO verificationCodeDTO){
        //调用service-driver-user服务查看司机信息是否存在
        ResponseResult responseResult = serviceDriverUserClient.checkDriverByPhone(verificationCodeDTO.getDriverPhone());
        if(responseResult.getData()==null){//如果司机不存在
            return responseResult;
        }

        //调用verificationcode服务获取验证码
        ResponseResult<NumberCodeResponse> numberCodeResponse = serviceVerificationCodeClient.getNumberCode(6);
        String verificationCode = numberCodeResponse.getData().getNumberCode();
        System.out.println(verificationCode);

        //验证码存入redis
        RedisUtil redisUtil = new RedisUtil(redisTemplate);
        redisUtil.insertRedis(redisUtil.generateDriverPhoneCode(verificationCodeDTO.getDriverPhone())
                , verificationCode + "", 2, TimeUnit.MINUTES);

        //响应
        VerificationCodeResponse response = new VerificationCodeResponse();
        response.setVerificationCode(verificationCode);
        return ResponseResult.success(response);
    }

    /**
     * 司机通过手机号登陆
     *
     * @param driverPhone
     * @param verificationCode
     * @return
     */
    public ResponseResult checkVerificationCode(String driverPhone, String verificationCode) {
        //获取验证码
        RedisUtil redisUtil = new RedisUtil(redisTemplate);
        String key = redisUtil.generateDriverPhoneCode(driverPhone);
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
        verificationCodeDTO.setPassengerPhone(driverPhone);

        //生成双token
        JwtUtil jwtUtil = new JwtUtil();
        String accessToken = jwtUtil.generateJWT(new JWTDTO(IdentityEnum.DRIVER.getIdentity(), driverPhone)
                , JWTTypeConstant.ACCESSTOKEN);
        String refreshToken = jwtUtil.generateJWT(new JWTDTO(IdentityEnum.DRIVER.getIdentity(), driverPhone)
                , JWTTypeConstant.REFRESHTOKEN);


        //将生成的双token放入redis
        redisUtil.insertRedis(redisUtil
                        .generateUserJWT(driverPhone, IdentityEnum.DRIVER.getIdentity(), JWTTypeConstant.ACCESSTOKEN)
                , accessToken, 10, TimeUnit.SECONDS);
        redisUtil.insertRedis(redisUtil
                        .generateUserJWT(driverPhone, IdentityEnum.DRIVER.getIdentity(), JWTTypeConstant.REFRESHTOKEN)
                , refreshToken, 60, TimeUnit.SECONDS);


        //返回
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken(accessToken);
        tokenResponse.setRefreshToken(refreshToken);

        return ResponseResult.success(tokenResponse);
    }


}
