package com.mxln.apiboss.service;

import com.mxln.apiboss.remote.ServiceDriverUserClient;
import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.request.DriverInfoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverService {

    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;

    /**
     * 请求service-driver-user服务完成新建司机数据
     * @param driverInfoRequest
     * @return
     */
    public ResponseResult driver(DriverInfoRequest driverInfoRequest){
        //请求service-driver-user服务
        ResponseResult responseResult = serviceDriverUserClient.driver(driverInfoRequest);

        //响应
        return responseResult;
    }

    /**
     * 请求service-driver-user服务完成新建司机数据
     * @param driverInfoRequest
     * @return
     */
    public ResponseResult putDriver(DriverInfoRequest driverInfoRequest){
        //请求service-driver-user服务
        ResponseResult responseResult = serviceDriverUserClient.putDriver(driverInfoRequest);

        //响应
        return responseResult;
    }

}
