package com.mxln.apidriver.remote;

import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.request.CarInfoRequest;
import com.mxln.innercommon.request.DriverInfoRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

@FeignClient("service-driver-user")
public interface ServiceDriverUserClient {

    @RequestMapping(method = RequestMethod.PUT,value = "/driver")
    ResponseResult putDriver(@RequestBody DriverInfoRequest driverInfoRequest);

    @RequestMapping(method = RequestMethod.GET,value = "/check-driver/{driverPhone}")
    ResponseResult checkDriverByPhone(@PathVariable("driverPhone") String phone);

    @RequestMapping(method = RequestMethod.POST,value = "/search-car")
    ResponseResult getCar(@RequestBody CarInfoRequest carInfoRequest);

}
