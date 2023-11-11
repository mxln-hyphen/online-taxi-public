package com.mxln.apidriver.remote;

import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.request.OrderStatusRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("service-order")
public interface ServiceOrderClient {

    @RequestMapping(method = RequestMethod.POST,value = "/driver-goto-passenger")
    ResponseResult driverGotoPassenger(@RequestBody OrderStatusRequest orderStatusRequest);

    @RequestMapping(method = RequestMethod.POST,value = "/driver-arrival-departure")
    ResponseResult driverArrivalDeparture(@RequestBody OrderStatusRequest orderStatusRequest);

    @RequestMapping(method = RequestMethod.POST,value = "/trip-start")
    ResponseResult tripStart(@RequestBody OrderStatusRequest orderStatusRequest);

    @RequestMapping(method = RequestMethod.POST,value = "/status/arrival-destination")
    ResponseResult ArrivalDestination(@RequestBody OrderStatusRequest orderStatusRequest);
}
