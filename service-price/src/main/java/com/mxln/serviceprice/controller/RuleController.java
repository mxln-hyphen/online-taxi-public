package com.mxln.serviceprice.controller;

import com.mxln.innercommon.dto.PriceRuleDTO;
import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.request.PriceRuleRequest;
import com.mxln.serviceprice.Service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RuleController {

    @Autowired
    private RuleService ruleService;

    @PostMapping("/rule/add")
    public ResponseResult ruleAdd(@RequestBody PriceRuleRequest priceRuleRequest){

        return ruleService.ruleAdd(priceRuleRequest);

    }
}
