package com.mxln.apipassenger.controller;

import com.mxln.apipassenger.request.VerificationCodeDTO;
import com.mxln.apipassenger.service.VerificationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class VerificationConroller {

    @Autowired
    VerificationCodeService verificationCodeService;

    @GetMapping ("/verification-code")
    public String verificationCode(@RequestBody VerificationCodeDTO verificationCodeDTO){
        String passengerPhoneNumber = verificationCodeDTO.getPassengerPhone();
        System.out.println("用户手机号："+verificationCodeDTO);
        return verificationCodeService.get(passengerPhoneNumber);
    }
}
