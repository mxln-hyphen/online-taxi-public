package com.mxln.apipassenger.service;

import com.mxln.apipassenger.remote.ServicePriceClient;
import com.mxln.innercommon.dto.ForecastPriceDTO;
import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.responses.priceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriceService {

    @Autowired
    private ServicePriceClient servicePriceClient;

    public ResponseResult<priceResponse> forecastPrice(ForecastPriceDTO forecastPriceDTO) {
        System.out.println("api-passenger:forecastPrice");

        //请求service-price获取价格
        ResponseResult<priceResponse> forecastPriceResponseResponseResult
                = servicePriceClient.forecastPrice(forecastPriceDTO);

        //响应
        String forecastPrice = forecastPriceResponseResponseResult.getData().getPrice();
        priceResponse priceResponse = new priceResponse();
        priceResponse.setPrice(forecastPrice);
        return ResponseResult.success(priceResponse);

    }
}
