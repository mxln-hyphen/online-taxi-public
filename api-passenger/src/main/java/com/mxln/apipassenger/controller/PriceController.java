package com.mxln.apipassenger.controller;

import com.mxln.apipassenger.service.PriceService;
import com.mxln.innercommon.dto.ForecastPriceDTO;
import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.request.ForecastPriceRequest;
import com.mxln.innercommon.responses.ForecastPriceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PriceController {

    @Autowired
    private PriceService priceService;

    @PostMapping("/forecast-price")
    public ResponseResult<ForecastPriceResponse> forecastPrice(@RequestBody ForecastPriceRequest forecastPriceRequest){
        ForecastPriceDTO forecastPriceDTO = new ForecastPriceDTO();
        forecastPriceDTO.setDepLatitude(forecastPriceRequest.getDepLatitude());
        forecastPriceDTO.setDepLongitude(forecastPriceRequest.getDepLongitude());
        forecastPriceDTO.setDestLatitude(forecastPriceRequest.getDestLatitude());
        forecastPriceDTO.setDestLongitude(forecastPriceRequest.getDestLongitude());


        return priceService.forecastPrice(forecastPriceDTO);
    }
}
