package com.mxln.serviceorder.controller;

import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.request.OrderStatusRequest;
import com.mxln.serviceorder.service.OrderStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/status")
public class OrderStatusController {

    @Autowired
    private OrderStatusService orderStatusService;

    @PostMapping("/driver-goto-passenger")
    public ResponseResult driverGotoPassenger(@RequestBody OrderStatusRequest orderStatusRequest){

        return orderStatusService.driverGotoPassenger(orderStatusRequest);
    }

    @PostMapping("/driver-arrival-departure")
    public ResponseResult driverArrivalDeparture(@RequestBody OrderStatusRequest orderStatusRequest){

        return orderStatusService.driverArrivalDeparture(orderStatusRequest);
    }

    @PostMapping("/trip-start")
    public ResponseResult tripStart(@RequestBody OrderStatusRequest orderStatusRequest){

        return orderStatusService.tripStart(orderStatusRequest);
    }

    @PostMapping("/arrival-destination")
    public ResponseResult ArrivalDestination(@RequestBody OrderStatusRequest orderStatusRequest){

        return orderStatusService.tripArrivalDestination(orderStatusRequest);
    }

}
