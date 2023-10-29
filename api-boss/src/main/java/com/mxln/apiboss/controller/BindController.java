package com.mxln.apiboss.controller;

import com.mxln.apiboss.service.BindService;
import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.request.DriverCarBindRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BindController {

    @Autowired
    private BindService bindService;

    @PostMapping("/driver-car-binding-relationship/bind")
    public ResponseResult bind(@RequestBody DriverCarBindRequest driverCarBindRequest){
        return bindService.bind(driverCarBindRequest);
    }

    @PostMapping("/driver-car-binding-relationship/unbind")
    public ResponseResult unbind(@RequestBody DriverCarBindRequest driverCarBindRequest){
        return bindService.unbind(driverCarBindRequest);
    }
}
