package com.mxln.servicepassengeruser.service;

import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.servicepassengeruser.mapper.PassengerUserMapper;
import com.mxln.servicepassengeruser.dto.PassengerUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private PassengerUserMapper passengerUserMapper;

    public ResponseResult loginOrRegister(String passengerPhone) {
        //查询用户信息
        HashMap<String, Object> map = new HashMap<>();
        map.put("passenger_phone", passengerPhone);
        List<PassengerUserDTO> passengerUserDTOS = passengerUserMapper.selectByMap(map);

        //若用户不存在，创建新用户
        if (passengerUserDTOS.size() == 0) {//用户不存在
            PassengerUserDTO newPassengerUser = new PassengerUserDTO();
            newPassengerUser.setPassengerName("李四");
            newPassengerUser.setPassengerGender((byte) 0);
            newPassengerUser.setGmt_create(LocalDateTime.now());
            newPassengerUser.setGmt_modified(LocalDateTime.now());
            newPassengerUser.setPassengerPhone(passengerPhone);
            newPassengerUser.setState((byte) 0);

            passengerUserMapper.insert(newPassengerUser);
        }


        //返回用户token

        return ResponseResult.success();
    }

}
