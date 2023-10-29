package com.mxln.apidriver.service;

import com.mxln.apidriver.remote.ServiceDriverUserClient;
import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.request.DriverInfoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;

    /**
     * 调用service-driver-user修改司机信息
     * @return
     */
    public ResponseResult driver(DriverInfoRequest driverInfoRequest){
        //请求service-driver-user服务
        ResponseResult responseResult = serviceDriverUserClient.putDriver(driverInfoRequest);

        //响应
        return responseResult;
    }

}
