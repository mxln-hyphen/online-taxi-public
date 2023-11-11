package com.mxln.serviceorder.remote;

import com.mxln.innercommon.Util.SsePrefixUtils;
import com.mxln.innercommon.request.PushRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@FeignClient("service-sse-push")
public interface ServiceSsePushClient {

    @RequestMapping(method = RequestMethod.GET,value = "/connect")
    SseEmitter connect(@RequestParam String userId, @RequestParam String identity);

    @RequestMapping(method = RequestMethod.POST,value = "/push")
    String push(@RequestBody PushRequest pushRequest);

    @RequestMapping(method = RequestMethod.GET,value = "/close")
    String close(@RequestParam String userId, @RequestParam String identity);

}

