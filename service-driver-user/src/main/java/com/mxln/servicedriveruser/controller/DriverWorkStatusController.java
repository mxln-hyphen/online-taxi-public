package com.mxln.servicedriveruser.controller;

import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.request.DriverWorkStatusRequest;
import com.mxln.servicedriveruser.service.DriverWorkStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DriverWorkStatusController {

    @Autowired
    private DriverWorkStatusService driverWorkStatusService;

    @PutMapping("/driver-work-status")
    public ResponseResult changeDriverWorkStatus(@RequestBody DriverWorkStatusRequest driverWorkStatusRequest){

        return driverWorkStatusService.changeDriverWorkStatus(driverWorkStatusRequest);

    }
}
