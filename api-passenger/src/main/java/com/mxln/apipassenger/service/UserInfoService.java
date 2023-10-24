package com.mxln.apipassenger.service;

import com.mxln.apipassenger.remote.ServicePassengerUserClient;
import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.dto.TokenResult;
import com.mxln.innercommon.responses.UserInfoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoService {

    @Autowired
    TokenService tokenService;

    @Autowired
    ServicePassengerUserClient servicePassengerUserClient;

    public ResponseResult<UserInfoResponse> search(String token){
        //解析token，提取用户信息
        TokenResult tokenResult = tokenService.analyzeToken(token);

        //通过用户手机号向service-passenger-user请求用户信息
        ResponseResult<UserInfoResponse> responseResult = servicePassengerUserClient.getUserInfo(tokenResult.getPhone());

        //响应
        return responseResult;
    }



}
