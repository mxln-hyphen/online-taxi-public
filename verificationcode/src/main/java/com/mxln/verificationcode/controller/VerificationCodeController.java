package com.mxln.verificationcode.controller;

import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.responses.NumberCodeResponse;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VerificationCodeController {


    @GetMapping("/numberCode/{size}")
    public ResponseResult numberCode(@PathVariable("size") int size) {
        System.out.println("验证码长度" + size);

        double ran = (Math.random() * 9 + 1) * Math.pow(10, size - 1);
        //通过随机数生成验证码
        String numbercode = String.valueOf((int)ran);

        NumberCodeResponse numberCodeResponse = new NumberCodeResponse();
        numberCodeResponse.setNumberCode(numbercode);


        return ResponseResult.success(numberCodeResponse);
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
