package com.mxln.apipassenger.controller;


import com.mxln.apipassenger.service.TokenService;
import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.request.TokenRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    @Autowired
    TokenService tokenService;

    @RequestMapping("/refresh-jwt")
    public ResponseResult refreshJWT(@RequestBody TokenRequest tokenRequest){
        System.out.println(tokenRequest.getRefreshToken());
        return tokenService.refresh(tokenRequest.getRefreshToken());
    }

}
