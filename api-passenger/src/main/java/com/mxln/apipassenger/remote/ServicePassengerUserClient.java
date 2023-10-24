package com.mxln.apipassenger.remote;

import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.request.VerificationCodeDTO;
import com.mxln.innercommon.responses.UserInfoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("service-passenger-user")
public interface ServicePassengerUserClient {

    @RequestMapping(method = RequestMethod.POST,value="/user")
    ResponseResult loginOrRegister(@RequestBody VerificationCodeDTO verificationCodeDTO);

    @GetMapping("/user/{passengerPhone}")
    ResponseResult<UserInfoResponse> getUserInfo(@PathVariable("passengerPhone")String passengerPhone);
}
