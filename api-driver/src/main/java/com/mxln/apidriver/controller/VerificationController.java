package com.mxln.apidriver.controller;

import com.mxln.apidriver.service.VerificationService;
import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.request.VerificationCodeDTO;
import com.mxln.innercommon.responses.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VerificationController {

    @Autowired
    private VerificationService verificationService;

    @GetMapping("/verification-code")
    public ResponseResult<VerificationCodeDTO> getVerificationCode(@RequestBody VerificationCodeDTO verificationCodeDTO){


        return verificationService.getVerificationCode(verificationCodeDTO);
    }

    @PostMapping("/verification-code")
    public ResponseResult<TokenResponse> checkVerificationCode(@RequestBody VerificationCodeDTO verificationCodeDTO){
        String driverPhone = verificationCodeDTO.getDriverPhone();
        String verificationCode = verificationCodeDTO.getVerificationCode();
        System.out.println(verificationCodeDTO.getDriverPhone());
        return verificationService.checkVerificationCode(driverPhone,verificationCode);
    }
}
