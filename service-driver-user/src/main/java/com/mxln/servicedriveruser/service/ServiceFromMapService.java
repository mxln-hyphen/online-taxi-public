package com.mxln.servicedriveruser.service;

import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.request.ServiceFromMapRequest;
import com.mxln.servicedriveruser.remote.ServiceFromMapClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceFromMapService {

    @Autowired
    private ServiceFromMapClient serviceFromMapClient;

    public ResponseResult serviceAdd(ServiceFromMapRequest serviceFromMapRequest){

        //通过高德地图猎鹰服务api创建服务
        serviceFromMapClient.serviceAdd(serviceFromMapRequest);

        //响应
        return ResponseResult.success();
    }

}
