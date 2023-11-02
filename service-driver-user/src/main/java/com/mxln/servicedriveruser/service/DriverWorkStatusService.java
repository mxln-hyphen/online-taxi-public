package com.mxln.servicedriveruser.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.mxln.innercommon.constant.DriverCarConstant;
import com.mxln.innercommon.dto.DriverWorkStatusDTO;
import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.request.DriverWorkStatusRequest;
import com.mxln.servicedriveruser.mapper.DriverWorkStatusMapper;
import org.omg.CORBA.LongHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
import java.time.LocalDateTime;

@Service
public class DriverWorkStatusService {

    @Autowired
    private DriverWorkStatusMapper driverWorkStatusMapper;

    public ResponseResult changeDriverWorkStatus(DriverWorkStatusRequest driverWorkStatusRequest) {

        //生成DriverWorkStatusDTO
        DriverWorkStatusDTO driverWorkStatusDTO=generateDriverWorkStatusDTO(driverWorkStatusRequest);

        //修改service-driver-user数据库driver_work_status表数据

        UpdateWrapper<DriverWorkStatusDTO> wrapper = new UpdateWrapper<>();
        wrapper.eq("driver_id",driverWorkStatusDTO.getDriverId());
        driverWorkStatusMapper.update(driverWorkStatusDTO,wrapper);

        //响应
        return ResponseResult.success();
    }

    public DriverWorkStatusDTO generateDriverWorkStatusDTO(DriverWorkStatusRequest driverWorkStatusRequest) {
        DriverWorkStatusDTO driverWorkStatusDTO = new DriverWorkStatusDTO();
        driverWorkStatusDTO.setDriverId(driverWorkStatusRequest.getDriverId());
        //校验工作状态是否合法
        Integer workStatus = driverWorkStatusRequest.getWorkStatus();
        if (workStatus > 0 && workStatus <= DriverCarConstant.DRIVER_WORK_STATUS_KIND){
            switch (workStatus){
                case 0:
                    driverWorkStatusDTO.setWorkStatus(DriverCarConstant.DRIVER_WORK_STATUS_RELEX);
                    break;
                case 1:
                    driverWorkStatusDTO.setWorkStatus(DriverCarConstant.DRIVER_WORK_STATUS_ORDER);
                    break;
                case 2:
                    driverWorkStatusDTO.setWorkStatus(DriverCarConstant.DRIVER_WORK_STATUS_PAUSE_ORDER);
                    break;
            }
        }
        driverWorkStatusDTO.setGmt_modified(LocalDateTime.now());

        return driverWorkStatusDTO;
    }

}
