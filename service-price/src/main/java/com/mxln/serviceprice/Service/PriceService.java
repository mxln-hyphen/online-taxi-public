package com.mxln.serviceprice.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mxln.innercommon.Util.BigDecimalUtil;
import com.mxln.innercommon.dto.DistanceDTO;
import com.mxln.innercommon.dto.ForecastPriceDTO;
import com.mxln.innercommon.dto.PriceRuleDTO;
import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.responses.DistanceResponse;
import com.mxln.innercommon.responses.priceResponse;
import com.mxln.serviceprice.mapper.PriceMapper;
import com.mxln.serviceprice.remote.ServiceMapClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.CharacterIterator;

@Service
public class PriceService {

    @Autowired
    private ServiceMapClient serviceMapClient;

    @Autowired
    private PriceMapper priceMapper;


    /**
     * 计算预估价格
     *
     * @param forecastPriceDTO
     * @return
     */
    public ResponseResult<priceResponse> forecastPrice(ForecastPriceDTO forecastPriceDTO) {
        //生成请求体
        DistanceDTO distanceDTO = generateDTO(forecastPriceDTO);

        //请求service-map获取距离信息
        ResponseResult<DistanceResponse> distanceResponseResponseResult = serviceMapClient.driving(distanceDTO);

        //获取最新版本计价规则
        PriceRuleDTO priceRuleDTO = getRecentPriceRule(forecastPriceDTO.getCityCode(), forecastPriceDTO.getVehicleType());

        //根据距离信息和计价规则计算预估价格
        double price = getPrice(priceRuleDTO,
                distanceResponseResponseResult.getData().getDistance(),
                distanceResponseResponseResult.getData().getDuration());

        //响应
        priceResponse priceResponse = new priceResponse();
        priceResponse.setPrice(String.valueOf(price));

        return ResponseResult.success(priceResponse);
    }

    /**
     * 计算具体价格
     * @param forecastPriceDTO
     * @return
     */
    public ResponseResult<priceResponse> price(ForecastPriceDTO forecastPriceDTO){
        //获取最新版本计价规则
        PriceRuleDTO priceRuleDTO = getRecentPriceRule(forecastPriceDTO.getCityCode(), forecastPriceDTO.getVehicleType());

        //根据距离信息和计价规则计算预估价格
        double price = getPrice(priceRuleDTO,
                forecastPriceDTO.getDistance(),
                forecastPriceDTO.getDuration());

        //响应
        priceResponse priceResponse = new priceResponse();
        priceResponse.setPrice(String.valueOf(price));

        return ResponseResult.success(priceResponse);
    }

    /**
     * 根据计价规则计算价格
     *
     * @param priceRuleDTO
     * @param distance
     * @param duration
     * @return
     */
    private double getPrice(PriceRuleDTO priceRuleDTO, int distance, int duration) {
        BigDecimal price = new BigDecimal(0).setScale(2,BigDecimal.ROUND_CEILING);
        //计算起步价
        price = price.add(new BigDecimal((double)priceRuleDTO.getStartFare()));

        //如果实际里程小于起步里程，里程价格为0
        if (distance <= priceRuleDTO.getStartMile()) {
            price = price.add(BigDecimal.valueOf(0));
        }
        //如果实际里程大于起步里程，里程价格为（实际里程-起步里程）*每公里程价格
        else {
            price = price.add(BigDecimalUtil.sub(distance, priceRuleDTO.getStartMile())
                    .divide(BigDecimal.valueOf(1000),2,BigDecimal.ROUND_CEILING)
                    .multiply(BigDecimal.valueOf(priceRuleDTO.getUnitPricePerMile())));
        }

        //时长价格为总时长*每分钟价格
        price = price.add(BigDecimal.valueOf(duration)
                .divide(BigDecimal.valueOf(60),2,BigDecimal.ROUND_CEILING)
                .multiply(BigDecimal.valueOf(priceRuleDTO.getUnitPricePerMinute())));

        return price.setScale(2,BigDecimal.ROUND_CEILING).doubleValue();
    }

    /**
     * 生成DistanceDTO请求体
     *
     * @param forecastPriceDTO
     * @return
     */
    private DistanceDTO generateDTO(ForecastPriceDTO forecastPriceDTO) {
        DistanceDTO distanceDTO = new DistanceDTO();
        distanceDTO.setDepLatitude(forecastPriceDTO.getDepLatitude());
        distanceDTO.setDepLongitude(forecastPriceDTO.getDepLongitude());
        distanceDTO.setDestLatitude(forecastPriceDTO.getDestLatitude());
        distanceDTO.setDestLongitude(forecastPriceDTO.getDestLongitude());
        return distanceDTO;
    }

    /**
     * 获取最新计价规则
     * @return
     */
    private PriceRuleDTO getRecentPriceRule(String ciyCode,String vehicleType){
        //获取最新版本计价规则，若不存在则将版本设置为1，否则将版本设置为原版本加1
        QueryWrapper<PriceRuleDTO> wrapper = new QueryWrapper<>();
        wrapper.eq("city_code", ciyCode);
        wrapper.eq("vehicle_type", vehicleType);
        wrapper.orderByDesc("fare_version");
        PriceRuleDTO priceRuleDTO = priceMapper.selectList(wrapper).get(0);
        return priceRuleDTO;
    }

}
