package com.mxln.apipassenger.service;

import com.mxln.innercommon.Util.RedisUtil;
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

    public static void main(String[] args) {
        String refreshToken ="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJwaG9uZSI6IjEzOTI0MjkxMDQ5IiwiaWRlbnRpdHkiOiIxMDAwIiwiZXhwaXJhdGlvbiI6MTY5ODA0NjA1NywidHlwZSI6ImFjY2VzcyJ9.t5MYTuTI_Ulcl98zTNOdxt1AjjY5XnZZl71PtH-vP48";
        TokenService tokenService = new TokenService();
        //解析refreshToken
        TokenResult decode = tokenService.analyzeToken(refreshToken);

        //检验refreshToken与redis中的token是否相等
        RedisUtil redisUtil = new RedisUtil(tokenService.redisTemplate);
        System.out.println(redisUtil
                .generateUserJWT(decode.getPhone(), decode.getIdentity(),decode.getType()));
    }

    /**
     * 校验refresh Token并返回双token
     * @param refreshToken
     * @return
     */
    public ResponseResult refresh(String refreshToken){
        //解析refreshToken
        TokenResult decode = analyzeToken(refreshToken);

        //检验refreshToken与redis中的token是否相等
        RedisUtil redisUtil = new RedisUtil(redisTemplate);
        System.out.println(redisUtil
                .generateUserJWT(decode.getPhone(), decode.getIdentity(),decode.getType()));
        String redistoken = redisUtil.get(redisUtil
                .generateUserJWT(decode.getPhone(), decode.getIdentity(),decode.getType()));

        if (StringUtils.isBlank(redistoken)) {
            return new ResponseResult().setCode(CommonStatusEnum.TOKEN_FAIL.getCode()).setMessage("token not exist");
        } else if (!redistoken.trim().equals(refreshToken.trim())) {
            return new ResponseResult().setCode(CommonStatusEnum.TOKEN_FAIL.getCode()).setMessage("token error");
        }

        //生成accessToken并存入redis
        JwtUtil jwtUtil = new JwtUtil();
        String accessToken = jwtUtil.generateJWT(new JWTDTO(IdentityEnum.PASSENGER.getIdentity(), decode.getPhone())
                , JWTTypeConstant.ACCESSTOKEN);
        redisUtil.insertRedis(redisUtil
                        .generateUserJWT(decode.getPhone(), IdentityEnum.PASSENGER.getIdentity(), JWTTypeConstant.ACCESSTOKEN)
                , accessToken, 10, TimeUnit.SECONDS);
        //返回双token
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken(accessToken);
        tokenResponse.setRefreshToken(refreshToken);

        return ResponseResult.success(tokenResponse);
    }

    /**
     * 解析Token
     * @param token
     * @return
     */
    public TokenResult analyzeToken(String token){
        //解析refreshToken
        JwtUtil jwtUtil = new JwtUtil();
        TokenResult decode=null;
        try {
            decode = jwtUtil.decode(token);
        }catch (Exception e){

        }
        return decode;
    }

}
