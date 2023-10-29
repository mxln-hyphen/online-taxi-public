package com.mxln.apiboss.service;

import com.mxln.apiboss.remote.ServiceDriverUserClient;
import com.mxln.innercommon.constant.CommonStatusEnum;
import com.mxln.innercommon.dto.DriverCarBindingRelationshipDTO;
import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.request.DriverCarBindRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BindService {

    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;

    /**
     * 根据车辆id与司机id绑定车辆与司机
     * @param driverCarBindRequest
     * @return
     */
    public ResponseResult bind(DriverCarBindRequest driverCarBindRequest){
        //请求Service-driver-user服务新增绑定信息
        serviceDriverUserClient.bind(driverCarBindRequest);

        //响应
        return ResponseResult.success();
    }

    /**
     * 根据车辆id与司机id解绑车辆与司机
     * @param driverCarBindRequest
     * @return
     */
    public ResponseResult unbind(DriverCarBindRequest driverCarBindRequest){
        //请求Service-driver-user服务删除绑定信息
        serviceDriverUserClient.unbind(driverCarBindRequest);

        //响应
        return ResponseResult.success();
    }
}
