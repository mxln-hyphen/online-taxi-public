package com.mxln.serviceprice.controller;

import com.mxln.innercommon.dto.ForecastPriceDTO;
import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.request.ForecastPriceRequest;
import com.mxln.innercommon.responses.ForecastPriceResponse;
import com.mxln.serviceprice.Service.PriceService;
import com.mxln.serviceprice.remote.ServiceMapClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PriceController {

    @Autowired
    private PriceService priceService;


    @PostMapping("/forecast-price")
    public ResponseResult<ForecastPriceResponse> forecastPrice(@RequestBody ForecastPriceDTO forecastPriceDTO){
        priceService.forecastPrice(forecastPriceDTO);
        return ResponseResult.success();
    }
}
