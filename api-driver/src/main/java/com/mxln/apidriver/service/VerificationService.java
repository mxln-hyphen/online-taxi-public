package com.mxln.apidriver.service;

import com.mxln.apidriver.remote.ServiceDriverUserClient;
import com.mxln.apidriver.remote.ServiceVerificationCodeClient;
import com.mxln.innercommon.Util.RedisUtil;
import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.request.VerificationCodeDTO;
import com.mxln.innercommon.responses.NumberCodeResponse;
import com.mxln.innercommon.responses.VerificationCodeResponse;
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


}
