package com.mxln.servicedriveruser.remote;

import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.request.TrackRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("service-map")
public interface ServiceMapClient {

    @RequestMapping(method = RequestMethod.POST,value = "/track/terminal/add")
    ResponseResult<TrackRequest> terminalAdd(@RequestBody TrackRequest trackRequest);

    @RequestMapping(method = RequestMethod.POST,value = "/track/trace/add")
    ResponseResult<TrackRequest> traceAdd(@RequestBody TrackRequest trackRequest);
}
