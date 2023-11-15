package com.mxln.apidriver.service;

import com.mxln.apidriver.remote.ServiceMapClient;
import com.mxln.apidriver.remote.ServiceOrderClient;
import com.mxln.apidriver.remote.ServicePriceClient;
import com.mxln.innercommon.dto.ForecastPriceDTO;
import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.request.OrderStatusRequest;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class OrderService {

    @Autowired
    private ServiceOrderClient serviceOrderClient;

    @Autowired
    private ServiceMapClient serviceMapClient;

    @Autowired
    private ServicePriceClient servicePriceClient;

    /**
     * 司机去接乘客
     * @param orderStatusRequest
     * @return
     */
    public ResponseResult driverGotoPassenger(OrderStatusRequest orderStatusRequest){
        //请求service-order
        ResponseResult responseResult = serviceOrderClient.driverGotoPassenger(orderStatusRequest);

        //响应
        return responseResult;
    }

    /**
     * 司机到达起点
     * @param orderStatusRequest
     * @return
     */
    public ResponseResult driverArrivalDeparture(OrderStatusRequest orderStatusRequest){
        //请求service-order
        ResponseResult responseResult = serviceOrderClient.driverArrivalDeparture(orderStatusRequest);

        //响应
        return responseResult;
    }

    /**
     * 司机接到乘客，行程开始
     * @param orderStatusRequest
     * @return
     */
    public ResponseResult tripStart( OrderStatusRequest orderStatusRequest){
        //请求service-order
        ResponseResult responseResult = serviceOrderClient.tripStart(orderStatusRequest);

        //响应
        return responseResult;
    }

    /**
     * 司机到达目的地，行程结束
     * @param orderStatusRequest
     * @return
     */
    public ResponseResult ArrivalDestination( OrderStatusRequest orderStatusRequest){
        //请求service-order修改订单状态
        ResponseResult responseResult = serviceOrderClient.ArrivalDestination(orderStatusRequest);

        //解析行程开始和结束时间
        JSONObject jsonObject = JSONObject.fromObject(responseResult.getData());
        String starttime = jsonObject.getString("pickUpPassengerTime");
        String endtime = jsonObject.getString("passengerGetoffTime");
        String cityCode = jsonObject.getString("address");
        String vehicleType = jsonObject.getString("vehicleType");

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            //将开始结束时间往外扩展比较时间转换带来的毫秒位置缺失
            starttime = String.valueOf(format.parse(starttime).getTime()-1000);
            endtime = String.valueOf(format.parse(endtime).getTime()+1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //请求service-map获取行程距离和时长
        Map<String,String> map = new HashMap<>();
        map.put("key","9e8f8ea4ba4cd6a55bfcd54d8b869f80");
        map.put("sid","1009278");
        map.put("tid","783635244");
        map.put("starttime",starttime);
        map.put("endtime",endtime);
        responseResult = serviceMapClient.traceSearch(map);

        //解析行程距离和时长
        jsonObject = JSONObject.fromObject(responseResult.getData());
        JSONArray tracks = jsonObject.getJSONArray("tracks");
        JSONObject track = (JSONObject) tracks.get(0);
        Integer distance = track.getInt("distance");
        Integer time = track.getInt("time");

        //去掉时间后三位，转化为以秒为单位
        StringBuilder builder = new StringBuilder(String.valueOf(time));
        Integer duration = Integer.parseInt(builder.substring(0,builder.length()-3));

        //请求service-price计算行程价格
        ForecastPriceDTO forecastPriceDTO = new ForecastPriceDTO();
        forecastPriceDTO.setCityCode(cityCode);
        forecastPriceDTO.setVehicleType(vehicleType);
        forecastPriceDTO.setDistance(distance);
        forecastPriceDTO.setDuration(duration);
        ResponseResult price = servicePriceClient.price(forecastPriceDTO);

        //响应
        return price;
    }

}
