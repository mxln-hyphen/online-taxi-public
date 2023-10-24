package com.mxln.apipassenger.service;

import com.mxln.apipassenger.remote.ServicePriceClient;
import com.mxln.innercommon.dto.ForecastPriceDTO;
import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.request.ForecastPriceRequest;
import com.mxln.innercommon.responses.ForecastPriceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriceService {

    @Autowired
    private ServicePriceClient servicePriceClient;

    public ResponseResult<ForecastPriceResponse> forecastPrice(ForecastPriceDTO forecastPriceDTO) {
        System.out.println("api-passenger:forecastPrice");
        //请求service-price获取价格
        servicePriceClient.forecastPrice(forecastPriceDTO);


        //响应
        return ResponseResult.success();

    }
}
