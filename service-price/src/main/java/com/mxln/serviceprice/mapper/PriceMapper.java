package com.mxln.serviceprice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mxln.innercommon.dto.PriceRuleDTO;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceMapper extends BaseMapper<PriceRuleDTO> {
}
