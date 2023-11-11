package com.mxln.serviceorder.service;

import com.mxln.innercommon.constant.OrderConstant;
import com.mxln.innercommon.dto.OrderInfoDTO;
import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.request.OrderStatusRequest;
import com.mxln.serviceorder.mapper.OrderInfoMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderStatusService {

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    /**
     * 司机去接乘客
     * @param orderStatusRequest
     * @return
     */
    public ResponseResult driverGotoPassenger(OrderStatusRequest orderStatusRequest){
        //获取订单信息
        OrderInfoDTO orderInfoDTO = orderInfoMapper.selectById(orderStatusRequest.getId());

        //装载新信息
        orderInfoDTO.setToPickUpPassengerTime(orderStatusRequest.getToPickUpPassengerTime());
        orderInfoDTO.setToPickUpPassengerLatitude(orderStatusRequest.getToPickUpPassengerLatitude());
        orderInfoDTO.setToPickUpPassengerLongitude(orderStatusRequest.getToPickUpPassengerLongitude());
        orderInfoDTO.setToPickUpPassengerAddress(orderStatusRequest.getToPickUpPassengerAddress());
        orderInfoDTO.setOrderStatus(OrderConstant.DRIVER_GOTO_PASSENGER);

        //写入数据库
        orderInfoMapper.updateById(orderInfoDTO);

        return ResponseResult.success();

    }

    /**
     * 司机到达乘客起点
     * @param orderStatusRequest
     * @return
     */
    public ResponseResult driverArrivalDeparture(OrderStatusRequest orderStatusRequest){
        //获取订单信息
        OrderInfoDTO orderInfoDTO = orderInfoMapper.selectById(orderStatusRequest.getId());

        //装载新信息
        orderInfoDTO.setDriverArrivedDepartureTime(orderStatusRequest.getDriverArrivedDepartureTime());
        orderInfoDTO.setOrderStatus(OrderConstant.DRIVER_ARRIVAL_DEPARTURE);

        //写入数据库
        orderInfoMapper.updateById(orderInfoDTO);

        return ResponseResult.success();
    }

    /**
     * 司机接到乘客,行程开始
     * @param orderStatusRequest
     * @return
     */
    public ResponseResult tripStart(OrderStatusRequest orderStatusRequest){
        //获取订单信息
        OrderInfoDTO orderInfoDTO = orderInfoMapper.selectById(orderStatusRequest.getId());

        //装载新信息
        orderInfoDTO.setPickUpPassengerTime(orderStatusRequest.getPickUpPassengerTime());
        orderInfoDTO.setPickUpPassengerLatitude(orderStatusRequest.getPickUpPassengerLatitude());
        orderInfoDTO.setPickUpPassengerLongitude(orderStatusRequest.getPickUpPassengerLongitude());
        orderInfoDTO.setOrderStatus(OrderConstant.TRIP_START);

        //写入数据库
        orderInfoMapper.updateById(orderInfoDTO);

        return ResponseResult.success();

    }

    /**
     * 司机到达目的地，行程结束
     * @param orderStatusRequest
     * @return
     */
    public ResponseResult tripArrivalDestination(OrderStatusRequest orderStatusRequest){
        //获取订单信息
        OrderInfoDTO orderInfoDTO = orderInfoMapper.selectById(orderStatusRequest.getId());

        //装载新信息
        orderInfoDTO.setPassengerGetoffTime(orderStatusRequest.getPassengerGetoffTime());
        orderInfoDTO.setPassengerGetoffLatitude(orderStatusRequest.getPassengerGetoffLatitude());
        orderInfoDTO.setPassengerGetoffLongitude(orderStatusRequest.getPassengerGetoffLongitude());
        orderInfoDTO.setOrderStatus(OrderConstant.ARRIVAL_DESTINATION);

        //写入数据库
        orderInfoMapper.updateById(orderInfoDTO);

        //响应
        return ResponseResult.success(orderInfoDTO);

    }

}
