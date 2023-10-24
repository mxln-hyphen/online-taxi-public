package com.mxln.servicemap.Service;


import com.mxln.innercommon.dto.DistanceDTO;
import com.mxln.innercommon.dto.ForecastPriceDTO;
import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.responses.DistanceResponse;
import com.mxln.servicemap.remote.ApiAmapClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DistanceService {

    @Autowired
    private ApiAmapClient apiAmapClient;

    public ResponseResult<DistanceResponse> driving(DistanceDTO distanceDTO){
        System.out.println("service-map:driving");
        //请求高德地图api获取距离信息
        DistanceResponse distanceResponse = apiAmapClient.getDistance(distanceDTO);

        //响应
        if(distanceResponse==null){
            return ResponseResult.fail();
        }
        return ResponseResult.success(distanceResponse);
    }

}
