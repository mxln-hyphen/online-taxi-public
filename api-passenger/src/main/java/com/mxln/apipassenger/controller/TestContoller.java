package com.mxln.apipassenger.controller;

import com.mxln.innercommon.dto.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestContoller {


    @GetMapping("/test")
    public String test(){
        return "test";
    }

    @GetMapping("/tokentest")
    public ResponseResult tokentest(){
        System.out.println(1111);
        return ResponseResult.success("tokentest");
    }

}
