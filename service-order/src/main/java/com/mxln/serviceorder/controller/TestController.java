package com.mxln.serviceorder.controller;

import com.mxln.innercommon.dto.OrderInfoDTO;
import com.mxln.serviceorder.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private OrderService orderService;

    @Value("${server.port}")
   String port;

    @GetMapping("/test/dispatchOrder/{orderId}")
    public String testDispatchOrder(@PathVariable("orderId")Long orderId){
        System.out.println("订单号："+orderId+",服务:"+port);


        orderService.test(orderId);

        return "success";

    }
}
