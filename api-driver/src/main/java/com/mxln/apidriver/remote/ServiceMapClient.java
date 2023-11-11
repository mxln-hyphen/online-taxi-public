package com.mxln.apidriver.remote;

import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.request.DriverInfoRequest;
import com.mxln.innercommon.request.TrackRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient("service-map")
public interface ServiceMapClient {

    @RequestMapping(method = RequestMethod.POST,value = "/track/point/upload")
    ResponseResult pointUpload(@RequestBody TrackRequest trackRequest);

    @RequestMapping(method = RequestMethod.GET,value = "/track/trace/search")
    ResponseResult traceSearch(@RequestParam Map<String,String> parammap);
}
