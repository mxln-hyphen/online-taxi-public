package com.mxln.apidriver.controller;

import com.mxln.apidriver.service.VerificationService;
import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.request.VerificationCodeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
}
