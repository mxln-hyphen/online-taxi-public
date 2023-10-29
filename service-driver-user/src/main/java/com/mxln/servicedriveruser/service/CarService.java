package com.mxln.servicedriveruser.service;

import com.mxln.innercommon.dto.CarDTO;
import com.mxln.innercommon.dto.DriverUserDTO;
import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.request.CarInfoRequest;
import com.mxln.servicedriveruser.mapper.CarMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarService {

    @Autowired
    private CarMapper carMapper;

    public ResponseResult car(CarInfoRequest carInfoRequest){
        //生成CarDTO实例
        CarDTO carDTO = generateCarDTO(carInfoRequest);

        //数据写入数据库
        carMapper.insert(carDTO);

        //响应
        return ResponseResult.success();
    }

    /**
     * 生成CarDTO实例
     * @param carInfoRequest
     * @return
     */
    private CarDTO generateCarDTO(CarInfoRequest carInfoRequest){
        CarDTO carDTO = new CarDTO();
        carDTO.setAddress(carInfoRequest.getAddress());
        carDTO.setVehicleNo(carInfoRequest.getVehicleNo());
        carDTO.setPlateColor(carInfoRequest.getPlateColor());
        carDTO.setSeats(carInfoRequest.getSeats());
        carDTO.setBrand(carInfoRequest.getBrand());
        carDTO.setModel(carInfoRequest.getModel());
        carDTO.setVehicleType(carInfoRequest.getVehicleType());
        carDTO.setOwnerName(carInfoRequest.getOwnerName());
        carDTO.setVehicleColor(carInfoRequest.getVehicleColor());
        carDTO.setEngineId(carInfoRequest.getEngineId());
        carDTO.setVin(carInfoRequest.getVin());
        carDTO.setCertifyDateA(carInfoRequest.getCertifyDateA());
        carDTO.setFueType(carInfoRequest.getFueType());
        carDTO.setEngineDisplace(carInfoRequest.getEngineDisplace());
        carDTO.setTransAgency(carInfoRequest.getTransAgency());
        carDTO.setTransArea(carInfoRequest.getTransArea());
        carDTO.setTransDateStart(carInfoRequest.getTransDateStart());
        carDTO.setTransDateEnd(carInfoRequest.getTransDateEnd());
        carDTO.setCertifyDateB(carInfoRequest.getCertifyDateB());
        carDTO.setFixState(carInfoRequest.getFixState());
        carDTO.setNextFixDate(carInfoRequest.getNextFixDate());
        carDTO.setCheckState(carInfoRequest.getCheckState());
        carDTO.setFeePrintId(carInfoRequest.getFeePrintId());
        carDTO.setGpsBrand(carInfoRequest.getGpsBrand());
        carDTO.setGpsModel(carInfoRequest.getGpsModel());
        carDTO.setGpsInstallDate(carInfoRequest.getGpsInstallDate());
        carDTO.setRegisterDate(carInfoRequest.getRegisterDate());
        carDTO.setCommercialType(carInfoRequest.getCommercialType());
        carDTO.setFareType(carInfoRequest.getFareType());
        carDTO.setState(carInfoRequest.getState());
        carDTO.setTid(carInfoRequest.getTid());
        carDTO.setTrid(carInfoRequest.getTrid());
        carDTO.setTrname(carInfoRequest.getTrname());
        carDTO.setGmtCreate(carInfoRequest.getGmtCreate());
        carDTO.setGmtModified(carInfoRequest.getGmtModified());
        return carDTO;
    }

}
