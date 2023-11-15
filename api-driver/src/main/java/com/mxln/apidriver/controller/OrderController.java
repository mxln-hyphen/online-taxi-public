package com.mxln.apidriver.controller;

import com.mxln.apidriver.service.OrderService;
import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.request.OrderStatusRequest;
import org.bouncycastle.pqc.jcajce.provider.qtesla.SignatureSpi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/driver-goto-passenger")
    public ResponseResult driverGotoPassenger(@RequestBody OrderStatusRequest orderStatusRequest){

        return orderService.driverGotoPassenger(orderStatusRequest);
    }

    @PostMapping("/driver-arrival-departure")
    public ResponseResult driverArrivalDeparture(@RequestBody OrderStatusRequest orderStatusRequest){

        return orderService.driverArrivalDeparture(orderStatusRequest);
    }

    @PostMapping("/trip-start")
    public ResponseResult tripStart(@RequestBody OrderStatusRequest orderStatusRequest){

        return orderService.tripStart(orderStatusRequest);
    }

    @PostMapping("/arrival-destination")
    public ResponseResult ArrivalDestination(@RequestBody OrderStatusRequest orderStatusRequest){

        return orderService.ArrivalDestination(orderStatusRequest);
    }
}
