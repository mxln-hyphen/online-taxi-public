package com.mxln.apipassenger.remote;

import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.request.OrderInfoRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("service-order")
public interface ServiceOrderClient {

    @RequestMapping(method = RequestMethod.POST,value = "/order/add")
    ResponseResult orderAdd(@RequestBody OrderInfoRequest orderInfoRequest);

    @RequestMapping(method = RequestMethod.GET,value = "/test/dispatchOrder/{orderId}")
    String testDispatch(@PathVariable Long orderId);

}
