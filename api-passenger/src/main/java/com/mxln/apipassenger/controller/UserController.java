package com.mxln.apipassenger.controller;

import com.mxln.apipassenger.service.UserInfoService;
import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.responses.UserInfoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController {

    @Autowired
    UserInfoService userInfoService;

    @GetMapping("/users")
    public ResponseResult users(HttpServletRequest servletRequest){
        System.out.println(1);
        String authorization = servletRequest.getHeader("Authorization");
        System.out.println(authorization);
        ResponseResult<UserInfoResponse> search = userInfoService.search(authorization);
        return search;
    }
}
