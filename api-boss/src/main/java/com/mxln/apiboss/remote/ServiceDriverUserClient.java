package com.mxln.apiboss.remote;

import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.request.DriverCarBindRequest;
import com.mxln.innercommon.request.DriverInfoRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("service-driver-user")
public interface ServiceDriverUserClient {

    @RequestMapping(method = RequestMethod.POST,value = "/driver")
    ResponseResult driver(@RequestBody DriverInfoRequest driverInfoRequest);

    @RequestMapping(method = RequestMethod.PUT,value = "/driver")
    ResponseResult putDriver(@RequestBody DriverInfoRequest driverInfoRequest);

    @RequestMapping(method = RequestMethod.POST,value = "/driver-car-binding-relationship/bind")
    ResponseResult bind(@RequestBody DriverCarBindRequest driverCarBindRequest);

    @RequestMapping(method = RequestMethod.POST,value = "/driver-car-binding-relationship/unbind")
    ResponseResult unbind(@RequestBody DriverCarBindRequest driverCarBindRequest);

}
