package com.mxln.servicepassengeruser.controller;

import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.request.VerificationCodeDTO;
import com.mxln.innercommon.responses.UserInfoResponse;
import com.mxln.servicepassengeruser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public ResponseResult user(@RequestBody VerificationCodeDTO verificationCodeDTO){
        return userService.loginOrRegister(verificationCodeDTO.getPassengerPhone());
    }

    @GetMapping("/user/{passengerPhone}")
    public ResponseResult<UserInfoResponse> user(@PathVariable String passengerPhone){
        System.out.println(passengerPhone);
        return userService.getUserInfo(passengerPhone);
    }

}
