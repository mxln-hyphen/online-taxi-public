package com.mxln.servicedriveruser.service;

import com.mxln.innercommon.dto.*;
import com.mxln.innercommon.request.CarInfoRequest;
import com.mxln.innercommon.request.DriverInfoRequest;
import com.mxln.innercommon.request.TrackRequest;
import com.mxln.innercommon.responses.DriverWorkInfoResponse;
import com.mxln.servicedriveruser.mapper.CarMapper;
import com.mxln.servicedriveruser.mapper.DriverCarBindingRelationshipMapper;
import com.mxln.servicedriveruser.mapper.DriverWorkStatusMapper;
import com.mxln.servicedriveruser.remote.ServiceMapClient;
import net.sf.json.JSONObject;
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
    private DriverCarBindingRelationshipMapper driverCarBindingRelationshipMapper;

    @Autowired
    private DriverWorkStatusMapper driverWorkStatusMapper;

    @Autowired
    private ServiceMapClient serviceMapClient;

    @Autowired
    private DriverService driverService;



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
     * 根据车辆id获取车辆信息
     * @param carId
     * @return
     */
    private CarDTO getCar(Long carId){
        return carMapper.selectById(carId);
    }

    /**
     * 根据车辆id获取司机信息
     * @param carId
     * @return
     */
    public ResponseResult getDriver(String carId){
        DriverWorkInfoResponse driverWorkInfoResponse = new DriverWorkInfoResponse();

        //根据车辆id获取车辆信息
        CarDTO car = getCar(Long.valueOf(carId));
        //根据车辆Id获取司机Id
        Long driverId = getDriverId(carId);
        driverWorkInfoResponse.setDriverId(driverId);
        //根据司机Id获取司机信息
        DriverInfoRequest driverInfoRequest = new DriverInfoRequest();
        driverInfoRequest.setId(String.valueOf(driverId));
        DriverUserDTO driver = driverService.getDriver(driverId);
        driverWorkInfoResponse.setDriverPhone(driver.getDriverPhone());
        //根据司机Id获取司机工作状态
        driverWorkInfoResponse.setDriverWorkState(getDriverWorkState(driverId));
        //获取其他信息
        driverWorkInfoResponse.setCarId(carId);
        driverWorkInfoResponse.setVehicleNo(car.getVehicleNo());
        driverWorkInfoResponse.setLicenseId(driver.getLicenseId());


        //响应
        return ResponseResult.success(driverWorkInfoResponse);
    }

    /**
     * 根据司机Id获取司机工作状态
     * @param driverID
     * @return
     */
    private int getDriverWorkState(Long driverID){
        HashMap<String, Object> map = new HashMap<>();
        map.put("driver_Id",driverID);
        List<DriverWorkStatusDTO> driverWorkStatusDTOS = driverWorkStatusMapper.selectByMap(map);
        return driverWorkStatusDTOS.get(0).getWorkStatus();
    }

    /**
     * 根据车辆Id获取司机Id
     * @param carId
     * @return
     */
    public Long getDriverId(String carId){
        HashMap<String, Object> map = new HashMap<>();
        map.put("car_id",carId);
        map.put("bind_state",1);
        List<DriverCarBindingRelationshipDTO> driverCarBindingRelationshipDTOS
                = driverCarBindingRelationshipMapper.selectByMap(map);
        return driverCarBindingRelationshipDTOS.get(0).getDriverId();
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
