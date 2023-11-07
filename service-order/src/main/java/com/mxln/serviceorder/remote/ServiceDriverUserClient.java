package com.mxln.serviceorder.remote;

import com.mxln.innercommon.dto.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("service-driver-user")
public interface ServiceDriverUserClient {

    @RequestMapping(method = RequestMethod.GET,value = "/search-car/driver/{carId}")
    ResponseResult getDriver(@PathVariable("carId")String carId);
}
