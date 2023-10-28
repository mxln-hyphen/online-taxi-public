package com.mxln.servicedriveruser.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mxln.innercommon.dto.DriverUserDTO;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverMapper extends BaseMapper<DriverUserDTO> {
}
