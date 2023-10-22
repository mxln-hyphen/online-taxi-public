package com.mxln.apipassenger.service;

import com.mxln.apipassenger.Util.RedisUtil;
import com.mxln.innercommon.Util.JwtUtil;
import com.mxln.innercommon.constant.CommonStatusEnum;
import com.mxln.innercommon.constant.IdentityEnum;
import com.mxln.innercommon.constant.JWTTypeConstant;
import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.dto.TokenResult;
import com.mxln.innercommon.request.JWTDTO;
import com.mxln.innercommon.responses.TokenResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class TokenService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 校验refresh Token并返回双token
     * @param refreshToken
     * @return
     */
    public ResponseResult refresh(String refreshToken){
        //解析refreshToken
        JwtUtil jwtUtil = new JwtUtil();
        TokenResult decode=null;
        try {
            decode = jwtUtil.decode(refreshToken);
        }catch (Exception e){

        }

        //检验refreshToken与redis中的token是否相等
        RedisUtil redisUtil = new RedisUtil(redisTemplate);
        String redistoken = redisUtil.get(redisUtil
                .generateUser(decode.getPhone(), decode.getIdentity(),decode.getType()));

        if (StringUtils.isBlank(redistoken)) {
            return new ResponseResult().setCode(CommonStatusEnum.TOKEN_FAIL.getCode()).setMessage("token not exist");
        } else if (!redistoken.trim().equals(refreshToken.trim())) {
            return new ResponseResult().setCode(CommonStatusEnum.TOKEN_FAIL.getCode()).setMessage("token error");
        }

        //生成accessToken并存入redis
        String accessToken = jwtUtil.generateJWT(new JWTDTO(IdentityEnum.PASSENGER.getIdentity(), decode.getPhone())
                , JWTTypeConstant.ACCESSTOKEN);
        redisUtil.insertRedis(redisUtil
                        .generateUser(decode.getPhone(), IdentityEnum.PASSENGER.getIdentity(), JWTTypeConstant.ACCESSTOKEN)
                , accessToken, 10, TimeUnit.SECONDS);
        //返回双token
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken(accessToken);
        tokenResponse.setRefreshToken(refreshToken);

        return ResponseResult.success(tokenResponse);
    }

}
