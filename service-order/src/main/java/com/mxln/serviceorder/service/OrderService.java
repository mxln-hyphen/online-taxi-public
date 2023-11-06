package com.mxln.serviceorder.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mxln.innercommon.constant.CommonStatusEnum;
import com.mxln.innercommon.constant.OrderConstant;
import com.mxln.innercommon.dto.OrderInfoDTO;
import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.request.OrderInfoRequest;
import com.mxln.serviceorder.mapper.OrderInfoMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.spi.CurrencyNameProvider;

@Service
public class OrderService {

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    /**
     * 创建订单
     * @param orderInfoRequest
     * @return
     */
    public ResponseResult orderAdd(OrderInfoRequest orderInfoRequest){
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

        //响应
        return ResponseResult.success();
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