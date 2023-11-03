package com.mxln.serviceorder.controller;

import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.request.OrderInfoRequest;
import com.mxln.serviceorder.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/order/add")
    public ResponseResult orderAdd(@RequestBody OrderInfoRequest orderInfoRequest){

        return orderService.orderAdd(orderInfoRequest);
    }

}
