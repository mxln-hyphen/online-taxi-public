package com.mxln.apipassenger.controller;

import com.mxln.apipassenger.service.OrderService;
import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.request.OrderInfoRequest;
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
