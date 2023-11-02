package com.mxln.apidriver.controller;

import com.mxln.apidriver.service.UserService;
import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.request.DriverInfoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("driver")
    public ResponseResult driver(@RequestBody DriverInfoRequest driverInfoRequest){

        return userService.driver(driverInfoRequest);
    }

    @GetMapping("/test")
    public ResponseResult test(){
        System.out.println("test");
        return ResponseResult.success();
    }

}
