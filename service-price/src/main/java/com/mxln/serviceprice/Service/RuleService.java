package com.mxln.serviceprice.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mxln.innercommon.constant.CommonStatusEnum;
import com.mxln.innercommon.dto.PriceRuleDTO;
import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.request.PriceRuleRequest;
import com.mxln.serviceprice.mapper.PriceMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.annotation.ElementType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RuleService {

    @Autowired
    private PriceMapper priceMapper;

    /**
     * 新增计价规则
     * @param priceRuleRequest
     * @return
     */
    public ResponseResult ruleAdd(PriceRuleRequest priceRuleRequest) {
        //生成PriceRuleDTO实例
        PriceRuleDTO priceRuleDTO = generatePriceRuleDTO(priceRuleRequest);

        //插入数据库
        priceMapper.insert(priceRuleDTO);

        //响应
        return ResponseResult.success();
    }

    /**
     * 查询城市编号和车型对应的计价规则是否存在
     * @param priceRuleRequest
     * @return
     */
    public ResponseResult ifExist(PriceRuleRequest priceRuleRequest){
        //查询计价规则是否存在
        if(getRecentPriceRule(priceRuleRequest.getCityCode(),priceRuleRequest.getVehicleType())==0){//如果最新版本号是0
            return ResponseResult.fail().setCode(CommonStatusEnum.PRICE_RULE_NOT_EXIST.getCode())
                    .setMessage(CommonStatusEnum.PRICE_RULE_NOT_EXIST.getMessage())
                    .setData(false);
        }

        //响应
        return ResponseResult.success(true);

    }

    /**
     * 生成PriceRuleDTO实例
     * @param priceRuleRequest
     * @return
     */
    private PriceRuleDTO generatePriceRuleDTO(PriceRuleRequest priceRuleRequest) {
        PriceRuleDTO priceRuleDTO = new PriceRuleDTO();
        BeanUtils.copyProperties(priceRuleRequest, priceRuleDTO);
        priceRuleDTO.setFareType(priceRuleDTO.getCityCode() + "$" + priceRuleDTO.getVehicleType());
        //将版本号设置为最新版本+1
        priceRuleDTO.setFareVersion(getRecentPriceRule
                (priceRuleRequest.getCityCode(),priceRuleRequest.getVehicleType())+1
        );

        return priceRuleDTO;
    }

    /**
     * 查询数据库获取最新计价规则
     * @return 返回最新版本号
     */
    private Integer getRecentPriceRule(String cityCode,String vehicleType){
        QueryWrapper<PriceRuleDTO> wrapper = new QueryWrapper<>();
        wrapper.eq("city_code", cityCode);
        wrapper.eq("vehicle_type", vehicleType);
        wrapper.orderByDesc("fare_version");
        List<PriceRuleDTO> priceRuleDTOS = priceMapper.selectList(wrapper);

        if (priceRuleDTOS.isEmpty()) {//不存在计价规则
            return 0;
        } else {//存在旧计价规则
            return priceRuleDTOS.get(0).getFareVersion();
        }
    }

}
