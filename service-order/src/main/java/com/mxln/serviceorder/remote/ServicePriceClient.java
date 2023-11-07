package com.mxln.serviceorder.remote;

import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.request.PriceRuleRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("service-price")
public interface ServicePriceClient {

    @RequestMapping(method = RequestMethod.POST,value = "/rule/if-exist")
    ResponseResult ifExist(@RequestBody PriceRuleRequest priceRuleRequest);

}
