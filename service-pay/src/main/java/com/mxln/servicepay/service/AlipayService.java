package com.mxln.servicepay.service;

import com.mxln.innercommon.request.OrderInfoRequest;
import com.mxln.innercommon.request.OrderStatusRequest;
import com.mxln.servicepay.remote.ServiceOrderClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlipayService {

    @Autowired
    ServiceOrderClient serviceOrderClient;

    public void pay(Long orderId){
        OrderStatusRequest orderRequest = new OrderStatusRequest();
        orderRequest.setId(orderId);
        serviceOrderClient.pay(orderRequest);

    }
}