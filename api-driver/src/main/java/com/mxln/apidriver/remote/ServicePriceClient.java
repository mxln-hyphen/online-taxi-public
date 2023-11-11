package com.mxln.apidriver.remote;

import com.mxln.innercommon.dto.ForecastPriceDTO;
import com.mxln.innercommon.dto.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("service-price")
public interface ServicePriceClient {

    @RequestMapping(method = RequestMethod.POST,value = "/price")
    ResponseResult price(@RequestBody ForecastPriceDTO forecastPriceDTO);
}
