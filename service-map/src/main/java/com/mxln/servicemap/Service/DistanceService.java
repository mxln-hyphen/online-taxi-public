package com.mxln.servicemap.Service;


import com.mxln.innercommon.dto.ForecastPriceDTO;
import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.responses.DistanceResponse;
import org.springframework.stereotype.Service;

@Service
public class DistanceService {

    public ResponseResult<DistanceResponse> driving(ForecastPriceDTO forecastPriceDTO){
        System.out.println("service-map:driving");
        //请求高德地图api获取距离信息

        //响应

        return ResponseResult.success();
    }

}
