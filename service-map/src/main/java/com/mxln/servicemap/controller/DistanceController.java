package com.mxln.servicemap.controller;

import com.mxln.innercommon.dto.ForecastPriceDTO;
import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.responses.DistanceResponse;
import com.mxln.servicemap.Service.DistanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DistanceController {

    @Autowired
    private DistanceService distanceService;

    @PostMapping("/direction/driving")
    public ResponseResult<DistanceResponse> driving(@RequestBody ForecastPriceDTO forecastPriceDTO){

        distanceService.driving(forecastPriceDTO);

        return ResponseResult.success();
    }

}
