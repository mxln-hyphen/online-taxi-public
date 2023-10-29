package com.mxln.servicedriveruser.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.mxln.innercommon.constant.CommonStatusEnum;
import com.mxln.innercommon.dto.DriverUserDTO;
import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.request.DriverInfoRequest;
import com.mxln.innercommon.request.VerificationCodeDTO;
import com.mxln.innercommon.responses.IsExistResponse;
import com.mxln.servicedriveruser.mapper.DriverMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DriverService {


    private static String DATE_TIME_FORMAT = "yyyy-MM-dd";

    @Autowired
    private DriverMapper driverMapper;

    /**
     * 将司机信息写入数据库
     *
     * @param driverInfoRequest
     * @return
     */
    public ResponseResult driver(DriverInfoRequest driverInfoRequest) {
        //生成DriverUserDTO实例
        DriverUserDTO driverUserDTO = generateDriverUserDTO(driverInfoRequest);

        //数据写入数据库
        driverMapper.insert(driverUserDTO);

        //响应
        return ResponseResult.success();

    }

    /**
     * 修改数据库中司机信息
     *
     * @param driverInfoRequest
     * @return
     */
    public ResponseResult putDriver(DriverInfoRequest driverInfoRequest) {
        //生成DriverUserDTO实例
        DriverUserDTO driverUserDTO = generateDriverUserDTO(driverInfoRequest);

        //构造查询器
        UpdateWrapper<DriverUserDTO> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", driverUserDTO.getId());

        //数据写入数据库
        driverMapper.update(driverUserDTO, wrapper);

        //响应
        return ResponseResult.success();
    }

    /**
     * 根据手机号查询司机是否存在
     *
     * @param phone
     * @return
     */
    public ResponseResult checkDriverByPhone(String phone) {
        //根据手机号查询driver表中的信息
        Map<String, Object> map = new HashMap<>();
        map.put("driver_phone", phone);
        List<DriverUserDTO> driverUserDTOS = driverMapper.selectByMap(map);


        //检查是否存在状态为1的记录
        boolean flag = false;
        for (DriverUserDTO driverUserDTO : driverUserDTOS) {
            if (driverUserDTO.getState() == 1) {
                flag = true;
            }
        }

        //响应
        if(flag){
            IsExistResponse isExistResponse = new IsExistResponse();
            isExistResponse.setIsExist(1);
            return ResponseResult.success(isExistResponse);
        }else {
            return new ResponseResult<>().setCode(CommonStatusEnum.DRIVER_NOT_EXIST.getCode())
                    .setMessage(CommonStatusEnum.DRIVER_NOT_EXIST.getMessage());
        }
    }


    /**
     * 生成写入数据库的DTO实例
     *
     * @param driverInfoRequest
     * @return
     */
    public DriverUserDTO generateDriverUserDTO(DriverInfoRequest driverInfoRequest) {
        DriverUserDTO driverUserDTO = new DriverUserDTO();
        driverUserDTO.setId(Integer.parseInt(driverInfoRequest.getId()));
        driverUserDTO.setAddress(driverInfoRequest.getAddress());
        driverUserDTO.setDriverName(driverInfoRequest.getDriverName());
        driverUserDTO.setDriverPhone(driverInfoRequest.getDriverPhone());
        driverUserDTO.setDriverGender(Byte.valueOf(driverInfoRequest.getDriverGender()));
        driverUserDTO.setDriverBirthday(generateLocalTime(driverInfoRequest.getDriverBirthday()));
        driverUserDTO.setDriverNation(driverInfoRequest.getDriverNation());
        driverUserDTO.setDriverContactAddress(driverInfoRequest.getDriverContactAddress());
        driverUserDTO.setLicenseId(driverInfoRequest.getLicenseId());
        driverUserDTO.setGetDriverLicenseDate(generateLocalTime(driverInfoRequest.getGetDriverLicenseDate()));
        driverUserDTO.setDriverLicenseOn(generateLocalTime(driverInfoRequest.getDriverLicenseOn()));
        driverUserDTO.setDriverLicenseOff(generateLocalTime(driverInfoRequest.getDriverLicenseOff()));
        driverUserDTO.setTaxiDriver(Byte.valueOf(driverInfoRequest.getTaxiDriver()));
        driverUserDTO.setCertificateNo(driverInfoRequest.getCertificateNo());
        driverUserDTO.setNetworkCarIssueOrganization(driverInfoRequest.getNetworkCarIssueOrganization());
        driverUserDTO.setNetworkCarIssueDate(generateLocalTime(driverInfoRequest.getNetworkCarIssueDate()));
        driverUserDTO.setGetNetworkCarProofDate(generateLocalTime(driverInfoRequest.getGetNetworkCarProofDate()));
        driverUserDTO.setNetworkCarProofOn(generateLocalTime(driverInfoRequest.getNetworkCarProofOn()));
        driverUserDTO.setNetworkCarProofOff(generateLocalTime(driverInfoRequest.getNetworkCarProofOff()));
        driverUserDTO.setRegisterDate(generateLocalTime(driverInfoRequest.getRegisterDate()));
        driverUserDTO.setCommercialType(Byte.valueOf(driverInfoRequest.getCommercialType()));
        driverUserDTO.setContractCompany(driverInfoRequest.getContractCompany());
        driverUserDTO.setContractOn(generateLocalTime(driverInfoRequest.getContractOn()));
        driverUserDTO.setContractOff(generateLocalTime(driverInfoRequest.getContractOff()));
        driverUserDTO.setState(Byte.valueOf(driverInfoRequest.getState()));
        driverUserDTO.setFlag(Byte.valueOf(driverInfoRequest.getFlag()));
        driverUserDTO.setUpdateTime(generateLocalTime(driverInfoRequest.getUpdateTime()));

        return driverUserDTO;
    }

    public LocalDate generateLocalTime(String time) {
        return LocalDate.parse(time, DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
    }

}
