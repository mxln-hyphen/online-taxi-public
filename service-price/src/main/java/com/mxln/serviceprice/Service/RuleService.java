package com.mxln.serviceprice.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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

    public ResponseResult ruleAdd(PriceRuleRequest priceRuleRequest) {
        //生成PriceRuleDTO实例
        PriceRuleDTO priceRuleDTO = generatePriceRuleDTO(priceRuleRequest);

        //插入数据库
        priceMapper.insert(priceRuleDTO);

        //响应
        return ResponseResult.success();
    }

    private PriceRuleDTO generatePriceRuleDTO(PriceRuleRequest priceRuleRequest) {
        PriceRuleDTO priceRuleDTO = new PriceRuleDTO();
        BeanUtils.copyProperties(priceRuleRequest, priceRuleDTO);
        priceRuleDTO.setFareType(priceRuleDTO.getCityCode() + "$" + priceRuleDTO.getVehicleType());
        //获取最新版本计价规则，若不存在则将版本设置为1，否则将版本设置为原版本加1
        QueryWrapper<PriceRuleDTO> wrapper = new QueryWrapper<>();
        wrapper.eq("city_code", priceRuleDTO.getCityCode());
        wrapper.eq("vehicle_type", priceRuleDTO.getVehicleType());
        wrapper.orderByDesc("fare_version");

        List<PriceRuleDTO> priceRuleDTOS = priceMapper.selectList(wrapper);
        if (priceRuleDTOS.isEmpty()) {//不存在计价规则
            priceRuleDTO.setFareVersion(1);
        } else {//存在旧计价规则
            priceRuleDTO.setFareVersion(priceRuleDTOS.get(0).getFareVersion() + 1);
        }
        return priceRuleDTO;
    }

}
