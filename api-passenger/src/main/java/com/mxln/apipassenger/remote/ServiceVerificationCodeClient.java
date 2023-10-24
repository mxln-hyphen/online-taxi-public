package com.mxln.apipassenger.remote;

import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.responses.NumberCodeResponse;
import com.mxln.innercommon.responses.UserInfoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("verificationcode")
public interface ServiceVerificationCodeClient {

    @RequestMapping(method = RequestMethod.GET,value="/numberCode/{size}")
    ResponseResult<NumberCodeResponse> getNumberCode(@PathVariable("size")int size);

}
