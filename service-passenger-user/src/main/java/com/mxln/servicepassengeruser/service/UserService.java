package com.mxln.servicepassengeruser.service;

import com.mxln.innercommon.constant.CommonStatusEnum;
import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.responses.UserInfoResponse;
import com.mxln.servicepassengeruser.mapper.PassengerUserMapper;
import com.mxln.innercommon.dto.PassengerUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private PassengerUserMapper passengerUserMapper;


    /**
     * 根据手机号在表中查询用户信息，如果不存在，则创建新用户
     *
     * @param passengerPhone
     * @return
     */
    public ResponseResult loginOrRegister(String passengerPhone) {
        //查询用户信息
        HashMap<String, Object> map = new HashMap<>();
        map.put("passenger_phone", passengerPhone);
        List<PassengerUserDTO> passengerUserDTOS = passengerUserMapper.selectByMap(map);

        //若用户不存在，创建新用户
        if (passengerUserDTOS.size() == 0) {//用户不存在
            PassengerUserDTO newPassengerUser = new PassengerUserDTO();
            newPassengerUser.setPassengerName("林子航");
            newPassengerUser.setPassengerGender((byte) 0);
            newPassengerUser.setGmt_create(LocalDateTime.now());
            newPassengerUser.setGmt_modified(LocalDateTime.now());
            newPassengerUser.setPassengerPhone(passengerPhone);
            newPassengerUser.setState((byte) 0);

            passengerUserMapper.insert(newPassengerUser);
        }

        return ResponseResult.success();
    }


    /**
     * 在数据库中查询用户信息
     *
     * @param passengerPhone
     * @return
     */
    public ResponseResult<UserInfoResponse> getUserInfo(String passengerPhone) {
        Map<String, Object> map = new HashMap<>();
        map.put("passenger_phone", passengerPhone);

        List<PassengerUserDTO> passengerUserDTOList = passengerUserMapper.selectByMap(map);

        if (passengerUserDTOList.size() == 0) {//用户不存在
            return new ResponseResult().setCode(CommonStatusEnum.USER_NOT_EXIST.getCode())
                    .setMessage(CommonStatusEnum.USER_NOT_EXIST.getMessage());
        } else for (PassengerUserDTO passengerUserDTO : passengerUserDTOList) {
            if (passengerUserDTO.getState() == 0) {
                UserInfoResponse userInfoResponse = generateReturn(passengerUserDTO);
                return ResponseResult.success(userInfoResponse);
            }else {//所有记录的该用户信息均已失效
                return new ResponseResult().setCode(CommonStatusEnum.USER_NOT_EXIST.getCode())
                        .setMessage(CommonStatusEnum.USER_NOT_EXIST.getMessage());
            }
        }


        return ResponseResult.fail();
    }

    /**
     * 根据数据库查询信息构造返回值
     * @param passengerUserDTO
     * @return
     */
    public UserInfoResponse generateReturn(PassengerUserDTO passengerUserDTO){
        UserInfoResponse userInfoResponse = new UserInfoResponse();
        userInfoResponse.setId(passengerUserDTO.getId());
        userInfoResponse.setPassengerName(passengerUserDTO.getPassengerName());
        userInfoResponse.setPassengerPhone(passengerUserDTO.getPassengerPhone());
        userInfoResponse.setPassengerGender(passengerUserDTO.getPassengerGender());
        userInfoResponse.setState(passengerUserDTO.getState());
        userInfoResponse.setProfilePhoto(passengerUserDTO.getProfilePhoto());
        userInfoResponse.setGmt_create(passengerUserDTO.getGmt_create());
        userInfoResponse.setGmt_modified(passengerUserDTO.getGmt_modified());
        return userInfoResponse;
    }
}
