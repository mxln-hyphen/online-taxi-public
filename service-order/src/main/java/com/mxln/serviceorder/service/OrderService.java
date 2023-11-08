package com.mxln.serviceorder.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mxln.innercommon.constant.CommonStatusEnum;
import com.mxln.innercommon.constant.DriverCarConstant;
import com.mxln.innercommon.constant.OrderConstant;
import com.mxln.innercommon.dto.OrderInfoDTO;
import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.request.OrderInfoRequest;
import com.mxln.innercommon.request.PriceRuleRequest;
import com.mxln.innercommon.request.TrackRequest;
import com.mxln.innercommon.responses.DriverWorkInfoResponse;
import com.mxln.serviceorder.mapper.OrderInfoMapper;
import com.mxln.serviceorder.remote.ServiceDriverUserClient;
import com.mxln.serviceorder.remote.ServiceMapClient;
import com.mxln.serviceorder.remote.ServicePriceClient;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.spi.CurrencyNameProvider;

@Service
public class OrderService {

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private ServicePriceClient servicePriceClient;

    @Autowired
    private ServiceMapClient serviceMapClient;

    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;

    @Autowired
    private RedissonClient redissonClient;

    @Value("${Amap.key}")
    private String key;

    @Value("${Amap.sid}")
    private Integer sid;

    /**
     * 创建订单
     * @param orderInfoRequest
     * @return
     */
    public ResponseResult orderAdd(OrderInfoRequest orderInfoRequest){
        //查询城市编号和对应车型的计价规则是否存在
        PriceRuleRequest priceRuleRequest = new PriceRuleRequest();
        priceRuleRequest.setCityCode(orderInfoRequest.getCityCode());
        priceRuleRequest.setVehicleType(orderInfoRequest.getVehicleType());
        ResponseResult responseResult = servicePriceClient.ifExist(priceRuleRequest);
        boolean flag = (boolean) responseResult.getData();
        if(!flag){
            return responseResult;
        }

        //查询是否已经存在有效订单
        QueryWrapper<OrderInfoDTO> wrapper = new QueryWrapper<>();
        wrapper.eq("passenger_id",orderInfoRequest.getPassengerId());
        wrapper.and(newwrapper->newwrapper.eq("order_status", OrderConstant.ORDER_INVALID)
                .or().eq("order_status", OrderConstant.DRIVER_TAKE_ORDER)
                .or().eq("order_status", OrderConstant.DRIVER_GOTO_PASSENGER)
                .or().eq("order_status", OrderConstant.DRIVER_ARRIVAL_DEPARTURE)
                .or().eq("order_status", OrderConstant.TRIP_START)
                .or().eq("order_status", OrderConstant.ARRIVAL_DESTINATION)
                .or().eq("order_status", OrderConstant.INITIATION_COLLECTION)
        );

        Integer count = orderInfoMapper.selectCount(wrapper);

        if (count > 0) {
            return ResponseResult.fail().setCode(CommonStatusEnum.ORDER_IS_EXIST.getCode())
                    .setMessage(CommonStatusEnum.ORDER_IS_EXIST.getMessage());
        }

        //生成OrderInfoDTO实例
        OrderInfoDTO orderInfoDTO = generateOrderInfoDTO(orderInfoRequest);

        //插入数据库
        orderInfoMapper.insert(orderInfoDTO);

        //开始派单
        //responseResult = dispatchOrder(orderInfoDTO);

        //响应
        return responseResult;
    }

    //测试
    public void test(Long id){
        //
        OrderInfoDTO orderInfoDTO = orderInfoMapper.selectById(id);
        dispatchOrder(orderInfoDTO);
    }

    /**
     * 派单
     * @param orderInfoDTO
     * @return
     */
    public ResponseResult dispatchOrder(OrderInfoDTO orderInfoDTO){
        //搜索附近是否有可接单司机
        DriverWorkInfoResponse driverWorkInfoResponse = searchCar(orderInfoDTO);
        if(driverWorkInfoResponse==null){//没有可接单司机
            //返回响应
            return ResponseResult.fail().setCode(CommonStatusEnum.ORDER_UNMATCHED_DRIVER.getCode())
                    .setMessage(CommonStatusEnum.ORDER_UNMATCHED_DRIVER.getMessage());
        }

        //将订单与司机匹配
        orderInfoDTO.setDriverId(driverWorkInfoResponse.getDriverId());
        orderInfoDTO.setDriverPhone(driverWorkInfoResponse.getDriverPhone());
        orderInfoDTO.setCarId(Long.valueOf(driverWorkInfoResponse.getCarId()));
        orderInfoDTO.setReceiveOrderTime(LocalDateTime.now());
        orderInfoDTO.setLicenseId(driverWorkInfoResponse.getLicenseId());
        orderInfoDTO.setVehicleNo(driverWorkInfoResponse.getVehicleNo());
        orderInfoDTO.setOrderStatus(OrderConstant.DRIVER_TAKE_ORDER);

        //更新数据库
        orderInfoMapper.updateById(orderInfoDTO);

        //返回响应
        return ResponseResult.success();
    }


    /**
     * 搜索附近是否有可接单司机
     * @param orderInfoDTO
     * @return
     */
    private DriverWorkInfoResponse searchCar(OrderInfoDTO orderInfoDTO){
        //调用service-map服务，按距离序列循环搜索附近是否有可用车辆
        List<Integer> distances = new ArrayList<>();
        distances.add(2000);
        distances.add(4000);
        distances.add(5000);

        TrackRequest trackRequest = new TrackRequest();
        trackRequest.setKey(key);
        trackRequest.setSid(sid);
        trackRequest.setCenter(orderInfoDTO.getDepLatitude()+","+orderInfoDTO.getDepLongitude());

        for (Integer distance : distances) {
            trackRequest.setRadius(String.valueOf(distance));
            ResponseResult responseResult = serviceMapClient.aroundSearch(trackRequest);

            JSONObject jsonObject = JSONObject.fromObject(responseResult.getData());
            int count = jsonObject.getInt("count");
            if(count==0){//如果该距离内没有司机
                System.out.printf("距离%d米内没有司机\n",distance);
                continue;
            }else {//如果该距离内有司机
                System.out.printf("距离%d米内有司机\n",distance);
                JSONArray result = jsonObject.getJSONArray("results");
                //在返回列表中找可以接单的司机
                for (int i = 0; i < count; i++) {
                    //解析车辆信息
                    JSONObject carInfo = (JSONObject) result.get(i);
                    String carId = carInfo.getString("desc");
                    //根据车辆id获取司机信息
                    ResponseResult driver = serviceDriverUserClient.getDriver(carId);
                    JSONObject driverJson = JSONObject.fromObject(driver.getData());
                    long driverId = driverJson.getLong("driverId");

                    String lockKey = (driverId+"".intern());
                    RLock lock = redissonClient.getLock(lockKey);
                    lock.lock();

                    //判断司机工作状态是否为接单
                    if(driverJson.getInt("driverWorkState")
                            == DriverCarConstant.DRIVER_WORK_STATUS_ORDER){//司机工作状态为接单
                        //判断司机是否有正在进行的订单
                        if (isOngoingOrder(driverId)) {//如果司机有正在进行的订单
                            System.out.printf("司机%d有正在进行的订单\n",driverId);
                            lock.unlock();
                            continue;
                        }
                        //返回司机信息
                        DriverWorkInfoResponse driverWorkInfoResponse = new DriverWorkInfoResponse();
                        driverWorkInfoResponse.setDriverId(driverId);
                        driverWorkInfoResponse.setDriverPhone(driverJson.getString("driverPhone"));
                        driverWorkInfoResponse.setCarId(carId);
                        driverWorkInfoResponse.setLicenseId(driverJson.getString("licenseId"));
                        driverWorkInfoResponse.setVehicleNo(driverJson.getString("vehicleNo"));
                        driverWorkInfoResponse.setDriverWorkState(DriverCarConstant.DRIVER_WORK_STATUS_ORDER);
                        lock.unlock();
                        return driverWorkInfoResponse;
                    }
                }

            }
        }
        //响应
        return null;
    }

    /**
     * 判断司机是否有正在进行的订单
     * @param driverId
     * @return 如果有正在进行的订单，返回true
     */
    private boolean isOngoingOrder(Long driverId){
        //判断司机是否有正在进行的订单
        HashMap<String, Object> map = new HashMap<>();
        map.put("driver_id",driverId);
        List<OrderInfoDTO> orderInfoDTOS = orderInfoMapper.selectByMap(map);
        for (OrderInfoDTO orderInfoDTO : orderInfoDTOS) {
            Integer orderStatus = orderInfoDTO.getOrderStatus();
            if(orderStatus==OrderConstant.DRIVER_TAKE_ORDER
                    ||orderStatus==OrderConstant.DRIVER_GOTO_PASSENGER
                    ||orderStatus==OrderConstant.DRIVER_ARRIVAL_DEPARTURE
                    ||orderStatus==OrderConstant.TRIP_START
                    ||orderStatus==OrderConstant.ARRIVAL_DESTINATION){
                return true;
            }
        }
        return false;
    }

    //生成OrderInfoDTO实例
    private OrderInfoDTO generateOrderInfoDTO(OrderInfoRequest orderInfoRequest){
        OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
        BeanUtils.copyProperties(orderInfoRequest,orderInfoDTO);
        //设置订单状态
        orderInfoDTO.setOrderStatus(OrderConstant.ORDER_INVALID);
        //订单发起时间
        orderInfoDTO.setOrderTime(LocalDateTime.now());
        //设置时间
        orderInfoDTO.setGmtCreate(LocalDateTime.now());
        orderInfoDTO.setGmtModified(LocalDateTime.now());

        return orderInfoDTO;
    }

}
