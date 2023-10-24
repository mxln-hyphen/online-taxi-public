package com.mxln.serviceprice.Service;

import com.mxln.innercommon.dto.ForecastPriceDTO;
import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.responses.ForecastPriceResponse;
import com.mxln.serviceprice.remote.ServiceMapClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriceService {

    @Autowired
    private ServiceMapClient serviceMapClient;


    /**
     * 计算预估价格
     * @param forecastPriceDTO
     * @return
     */
    public ResponseResult<ForecastPriceResponse> forecastPrice(ForecastPriceDTO forecastPriceDTO) {
        System.out.println("service-price:forecastPrice");
        //请求service-map获取距离信息
        serviceMapClient.driving(forecastPriceDTO);

        //根据距离信息和计价规则计算预估价格

        //响应
        return ResponseResult.success();

    }


}
