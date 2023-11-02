package com.mxln.servicedriveruser.controller;

import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.request.ServiceFromMapRequest;
import com.mxln.servicedriveruser.service.ServiceFromMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceController {

    @Autowired
    private ServiceFromMapService serviceFromMapService;

    @PostMapping("/service/add")
    public ResponseResult serviceAdd(@RequestBody ServiceFromMapRequest serviceFromMapRequest){

        System.out.println(serviceFromMapRequest.getName());

        serviceFromMapService.serviceAdd(serviceFromMapRequest);

        return ResponseResult.success();
    }

}
