package com.mxln.serviceprice.controller;

import com.mxln.innercommon.dto.ForecastPriceDTO;
import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.responses.priceResponse;
import com.mxln.serviceprice.Service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PriceController {

    @Autowired
    private PriceService priceService;


    @PostMapping("/forecast-price")
    public ResponseResult<priceResponse> forecastPrice(@RequestBody ForecastPriceDTO forecastPriceDTO){

        return priceService.forecastPrice(forecastPriceDTO);
    }

    @PostMapping("/price")
    public ResponseResult<priceResponse> price(@RequestBody ForecastPriceDTO forecastPriceDTO){

        return priceService.price(forecastPriceDTO);
    }
}
