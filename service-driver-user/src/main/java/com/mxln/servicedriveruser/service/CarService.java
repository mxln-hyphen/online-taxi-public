package com.mxln.servicedriveruser.service;

import com.mxln.innercommon.dto.CarDTO;
import com.mxln.innercommon.dto.DriverUserDTO;
import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.request.CarInfoRequest;
import com.mxln.innercommon.request.DriverInfoRequest;
import com.mxln.innercommon.request.TrackRequest;
import com.mxln.servicedriveruser.mapper.CarMapper;
import com.mxln.servicedriveruser.remote.ServiceMapClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CarService {

    @Value("${Amap.key}")
    private String key;

    @Value("${Amap.sid}")
    private Integer sid;

    @Autowired
    private CarMapper carMapper;

    @Autowired
    private ServiceMapClient serviceMapClient;

    /**
     * 创建车辆信息
     * @param carInfoRequest
     * @return
     */
    public ResponseResult car(CarInfoRequest carInfoRequest){
        //根据车牌号生成tid
        TrackRequest trackRequest = new TrackRequest();
        trackRequest.setName(carInfoRequest.getVehicleNo());
        trackRequest.setKey(key);
        trackRequest.setSid(sid);
        ResponseResult<TrackRequest> trackRequestResponseResult = serviceMapClient.terminalAdd(trackRequest);
        trackRequest = trackRequestResponseResult.getData();
        carInfoRequest.setTid(String.valueOf(trackRequest.getTid()));

        //根据tid生成trid
        trackRequestResponseResult = serviceMapClient.traceAdd(trackRequest);
        trackRequest = trackRequestResponseResult.getData();
        carInfoRequest.setTrid(String.valueOf(trackRequest.getTrid()));


        //生成CarDTO实例
        CarDTO carDTO = generateCarDTO(carInfoRequest);

        //数据写入数据库
        carMapper.insert(carDTO);

        //响应
        return ResponseResult.success();
    }

    /**
     * 根据车辆id查询车辆信息
     * @param carInfoRequest
     * @return
     */
    public ResponseResult getCar(CarInfoRequest carInfoRequest) {
        //根据车辆id查询driver表中的信息
        Map<String, Object> map = new HashMap<>();
        map.put("id",carInfoRequest.getId());
        List<CarDTO> driverUserDTOS = carMapper.selectByMap(map);

        //响应
        return ResponseResult.success(driverUserDTOS.get(0));
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
