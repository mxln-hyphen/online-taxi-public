package com.mxln.servicedriveruser.controller;

import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.servicedriveruser.service.CityDriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/city-driver")
public class CityDriverController {

    @Autowired
    private CityDriverService cityDriverService;

    @GetMapping("/is-active-driver")
    public ResponseResult isActiveDriver(String cityCode){
        System.out.println(cityCode);
        return cityDriverService.isActiveDriver(cityCode);
    }

}
