package com.mxln.serviceprice.controller;

import com.mxln.innercommon.dto.PriceRuleDTO;
import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.request.PriceRuleRequest;
import com.mxln.serviceprice.Service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rule")
public class RuleController {

    @Autowired
    private RuleService ruleService;

    @PostMapping("/add")
    public ResponseResult ruleAdd(@RequestBody PriceRuleRequest priceRuleRequest){

        return ruleService.ruleAdd(priceRuleRequest);
    }

    @GetMapping("/if-exist")
    public ResponseResult<Boolean> ifExist(@RequestBody PriceRuleRequest priceRuleRequest){
        return  ruleService.ifExist(priceRuleRequest);
    }
}
