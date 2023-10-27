package com.mxln.servicemap.controller;

import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.servicemap.Service.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DistrictController {

    @Autowired
    private DistrictService districtService;

    @GetMapping("/init-district")
    public ResponseResult initDistrict(){
        districtService.initDistrict();

        return ResponseResult.success();
    }

}
