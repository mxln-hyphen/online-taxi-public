package com.mxln.apipassenger.controller;

import com.mxln.innercommon.request.VerificationCodeDTO;
import com.mxln.apipassenger.service.VerificationCodeService;
import com.mxln.innercommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class VerificationController {

    @Autowired
    VerificationCodeService verificationCodeService;

    @GetMapping("/verification-code")
    public ResponseResult getVerificationCode(@RequestBody VerificationCodeDTO verificationCodeDTO) {
        String passengerPhoneNumber = verificationCodeDTO.getPassengerPhone();
        System.out.println(verificationCodeDTO.getPassengerPhone());
        return verificationCodeService.get(passengerPhoneNumber);
    }

    @PostMapping("/verification-code-check")
    public ResponseResult checkVerificationCode(@RequestBody VerificationCodeDTO verificationCodeDTO){
        String passengerPhoneNumber = verificationCodeDTO.getPassengerPhone();
        String verificationCode = verificationCodeDTO.getVerificationCode();
        System.out.println(verificationCodeDTO.getPassengerPhone());
        return verificationCodeService.check(passengerPhoneNumber,verificationCode);
    }
}
