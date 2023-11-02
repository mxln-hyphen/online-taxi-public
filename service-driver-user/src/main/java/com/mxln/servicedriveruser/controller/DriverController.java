package com.mxln.servicedriveruser.controller;

import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.request.DriverInfoRequest;
import com.mxln.innercommon.request.VerificationCodeDTO;
import com.mxln.innercommon.responses.IsExistResponse;
import com.mxln.servicedriveruser.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class DriverController {

    @Autowired
    private DriverService driverService;

    @PostMapping("/driver")
    public ResponseResult driver(@RequestBody DriverInfoRequest driverInfoRequest){

        return driverService.driver(driverInfoRequest);
    }

    @PutMapping("/driver")
    public ResponseResult putDriver(@RequestBody DriverInfoRequest driverInfoRequest){

        return driverService.putDriver(driverInfoRequest);
    }

    @GetMapping("/driver")
    public ResponseResult getDriver(@RequestBody DriverInfoRequest driverInfoRequest) {
        return driverService.getDriver(driverInfoRequest);
    }

    @GetMapping("/check-driver/{driverPhone}")
    public ResponseResult<IsExistResponse> checkDriver(@PathVariable("driverPhone")String driverPhone){

        return driverService.checkDriverByPhone(driverPhone);
    }

}
