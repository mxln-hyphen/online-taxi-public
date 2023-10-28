package com.mxln.servicedriveruser.controller;

import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.request.DriverInfoRequest;
import com.mxln.servicedriveruser.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

}
