package com.mxln.servicedriveruser.controller;

import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.request.CarInfoRequest;
import com.mxln.servicedriveruser.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CarController {

    @Autowired
    private CarService carservice;

    @PostMapping("/car")
    public ResponseResult car(@RequestBody CarInfoRequest carInfoRequest){

        carservice.car(carInfoRequest);

        return ResponseResult.success();
    }

    @PostMapping("/search-car")
    public ResponseResult getCar(@RequestBody CarInfoRequest carInfoRequest){

        return carservice.getCar(carInfoRequest);
    }

    @GetMapping("/search-car/driver/{carId}")
    public ResponseResult getDriver(@PathVariable("carId")String carId){

        return carservice.getDriver(carId);
    }

}
