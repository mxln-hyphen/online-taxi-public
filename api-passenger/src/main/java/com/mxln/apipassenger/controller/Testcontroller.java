package com.mxln.apipassenger.controller;

import com.mxln.apipassenger.remote.ServiceOrderClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Testcontroller {

    @Autowired
    private ServiceOrderClient serviceOrderClient;

    @GetMapping("/test/dispatch/{orderId}")
    public String testDispatch(@PathVariable Long orderId){
        System.out.println("订单："+orderId);

        serviceOrderClient.testDispatch(orderId);

        return "success";
    }
}
