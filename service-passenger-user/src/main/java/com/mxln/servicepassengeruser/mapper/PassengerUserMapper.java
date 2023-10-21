package com.mxln.servicepassengeruser.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mxln.servicepassengeruser.dto.PassengerUserDTO;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerUserMapper extends BaseMapper<PassengerUserDTO> {
}
