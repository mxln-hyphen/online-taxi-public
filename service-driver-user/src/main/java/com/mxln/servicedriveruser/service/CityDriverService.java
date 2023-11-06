package com.mxln.servicedriveruser.service;

import com.mxln.innercommon.constant.CommonStatusEnum;
import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.servicedriveruser.mapper.DriverMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityDriverService {

    @Autowired
    private DriverMapper driverMapper;

    /**
     * 查询对应城市是否有司机可用
     * @param cityCode
     * @return
     */
    public ResponseResult isActiveDriver(String cityCode){
        //查询对应城市可用司机人数
        Integer count = driverMapper.select1(cityCode);

        System.out.println(count);

        //响应
        if(count==0){
            return ResponseResult.fail().setMessage(CommonStatusEnum.CITY_HAS_NO_ACTIVE_DRIVER.getMessage())
                    .setCode(CommonStatusEnum.CITY_HAS_NO_ACTIVE_DRIVER.getCode());
        }else
            return ResponseResult.success();

    }




}
