package com.mxln.servicedriveruser.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.mxln.innercommon.constant.CommonStatusEnum;
import com.mxln.innercommon.dto.DriverCarBindingRelationshipDTO;
import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.request.DriverCarBindRequest;
import com.mxln.servicedriveruser.mapper.DriverCarBindingRelationshipMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BindService {

    @Autowired
    private DriverCarBindingRelationshipMapper driverCarBindingRelationshipMapper;

    /**
     * 根据车辆id与司机id绑定车辆与司机
     * @param driverCarBindRequest
     * @return
     */
    public ResponseResult bind(DriverCarBindRequest driverCarBindRequest){
        //查询车辆是否已经被绑定
        Map<String,Object> map = new HashMap<>();
        map.put("car_id",driverCarBindRequest.getCarId());
        List<DriverCarBindingRelationshipDTO> driverCarBindingRelationshipDTOS =
                driverCarBindingRelationshipMapper.selectByMap(map);
        for (DriverCarBindingRelationshipDTO driverCarBindingRelationshipDTO : driverCarBindingRelationshipDTOS) {
            if(driverCarBindingRelationshipDTO.getBindState()==1){//如果已被绑定
                return new ResponseResult().setCode(CommonStatusEnum.CAR_IS_BIND.getCode())
                        .setMessage(CommonStatusEnum.CAR_IS_BIND.getMessage());
            }
        }

        //将请求封装进DriverCarBindingRelationshipDTO类
        DriverCarBindingRelationshipDTO driverCarBindingRelationshipDTO
                = generateDriverCarBindingRelationshipDTO(driverCarBindRequest);

        //插入数据库
        driverCarBindingRelationshipMapper.insert(driverCarBindingRelationshipDTO);

        //响应
        return ResponseResult.success();
    }

    /**
     * 根据车辆id与司机id解绑车辆与司机
     * @param driverCarBindRequest
     * @return
     */
    public ResponseResult unbind(DriverCarBindRequest driverCarBindRequest){
        //查询车辆是否已经被绑定，如果未被绑定，返回错误信息
        Map<String,Object> map = new HashMap<>();
        map.put("car_id",driverCarBindRequest.getCarId());
        List<DriverCarBindingRelationshipDTO> driverCarBindingRelationshipDTOS =
                driverCarBindingRelationshipMapper.selectByMap(map);
        DriverCarBindingRelationshipDTO deleteDTO = null;
        boolean flag = false;
        for (DriverCarBindingRelationshipDTO driverCarBindingRelationshipDTO : driverCarBindingRelationshipDTOS) {
            if(driverCarBindingRelationshipDTO.getBindState()==1){//如果存在绑定信息
                deleteDTO=driverCarBindingRelationshipDTO;
                flag = true;
            }
        }
        if(!flag){//如果不存在绑定信息
            return new ResponseResult().setCode(CommonStatusEnum.CAR_IS_NOT_BIND.getCode())
                    .setMessage(CommonStatusEnum.CAR_IS_NOT_BIND.getMessage());
        }

        //修改要删除的行信息
        deleteDTO.setBindState(0);
        deleteDTO.setUnBindingTime(LocalDateTime.now());

        //更新数据库信息，将绑定状态设置为0
        UpdateWrapper<DriverCarBindingRelationshipDTO> wrapper = new UpdateWrapper<>();
        wrapper.eq("id",deleteDTO.getId());
        int update = driverCarBindingRelationshipMapper.update(deleteDTO, wrapper);

        //响应
        return ResponseResult.success();
    }

    /**
     * 构造DriverCarBindingRelationshipDTO类
     * @param driverCarBindRequest
     * @return
     */
    private DriverCarBindingRelationshipDTO generateDriverCarBindingRelationshipDTO(DriverCarBindRequest driverCarBindRequest){
        DriverCarBindingRelationshipDTO driverCarBindingRelationshipDTO = new DriverCarBindingRelationshipDTO();
        driverCarBindingRelationshipDTO.setDriverId(driverCarBindRequest.getDriverId());
        driverCarBindingRelationshipDTO.setCarId(driverCarBindRequest.getCarId());
        driverCarBindingRelationshipDTO.setBindState(1);
        driverCarBindingRelationshipDTO.setBindingTime(LocalDateTime.now());
        return driverCarBindingRelationshipDTO;
    }

}
