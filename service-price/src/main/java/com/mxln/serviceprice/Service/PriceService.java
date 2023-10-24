package com.mxln.serviceprice.Service;

import com.mxln.innercommon.Util.BigDecimalUtil;
import com.mxln.innercommon.dto.DistanceDTO;
import com.mxln.innercommon.dto.ForecastPriceDTO;
import com.mxln.innercommon.dto.PriceRuleDTO;
import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.responses.DistanceResponse;
import com.mxln.innercommon.responses.ForecastPriceResponse;
import com.mxln.serviceprice.mapper.PriceMapper;
import com.mxln.serviceprice.remote.ServiceMapClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

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
    public ResponseResult<ForecastPriceResponse> forecastPrice(ForecastPriceDTO forecastPriceDTO) {
        System.out.println("service-price:forecastPrice");
        //生成请求体
        DistanceDTO distanceDTO = generateDTO(forecastPriceDTO);

        //请求service-map获取距离信息
        ResponseResult<DistanceResponse> distanceResponseResponseResult = serviceMapClient.driving(distanceDTO);

        //获取计价规则
        Map<String, Object> map = new HashMap<>();
        map.put("city_code", 1);
        PriceRuleDTO priceRuleDTO = priceMapper.selectByMap(map).get(0);

        //根据距离信息和计价规则计算预估价格
        double price = getPrice(priceRuleDTO,
                distanceResponseResponseResult.getData().getDistance(),
                distanceResponseResponseResult.getData().getDuration());

        //响应
        ForecastPriceResponse forecastPriceResponse = new ForecastPriceResponse();
        forecastPriceResponse.setForecastPrice(String.valueOf(price));

        return ResponseResult.success(forecastPriceResponse);
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


}
