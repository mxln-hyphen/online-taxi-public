package com.mxln.apipassenger.interceptor;


import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.mxln.innercommon.Util.RedisUtil;
import com.mxln.innercommon.Util.JwtUtil;
import com.mxln.innercommon.constant.CommonStatusEnum;
import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.dto.TokenResult;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class JWTInterceptor implements HandlerInterceptor {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeader("Authorization");
        boolean flag = false;
        String errorMessage = "";

        TokenResult decode = null;

        try {
            //从用户的token解析出用户信息
            decode = new JwtUtil().decode(token);
            flag = true;
        } catch (SignatureVerificationException e) {
            errorMessage = "token sign error";
        } catch (TokenExpiredException e) {
            errorMessage = "token time out";
        } catch (AlgorithmMismatchException e) {
            errorMessage = "token AlgorithmMismatchException";
        } catch (Exception e) {
            errorMessage = "token invalid";
        }


        if (flag) {
            //根据用户信息从redis获取token
            RedisUtil redisUtil = new RedisUtil(redisTemplate);
            String redistoken = redisUtil.get(redisUtil
                    .generateUserJWT(decode.getPhone(), decode.getIdentity(), decode.getType()));

            if (StringUtils.isBlank(redistoken)) {
                errorMessage = "token not exist";
                flag = false;
            } else if (!redistoken.trim().equals(token.trim())) {
                errorMessage = "token error";
                flag = false;
            }
        }

        if (!flag) {
            PrintWriter writer = response.getWriter();
            ResponseResult responseResult = new ResponseResult()
                    .setCode(CommonStatusEnum.TOKEN_FAIL.getCode())
                    .setMessage(errorMessage);
            writer.print(JSONObject.fromObject(responseResult));
        }
        return flag;
    }
}
