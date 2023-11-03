package com.mxln.apipassenger.service;

import com.mxln.apipassenger.remote.ServiceOrderClient;
import com.mxln.innercommon.dto.OrderInfoDTO;
import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.request.OrderInfoRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private ServiceOrderClient serviceOrderClient;

    /**
     * 请求service-order服务创建订单
     * @param orderInfoRequest
     * @return
     */
    public ResponseResult orderAdd(OrderInfoRequest orderInfoRequest){
        //请求service-order服务创建订单
        ResponseResult responseResult = serviceOrderClient.orderAdd(orderInfoRequest);

        //响应
        return responseResult;
    }
}
