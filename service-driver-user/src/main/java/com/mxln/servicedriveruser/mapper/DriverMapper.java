package com.mxln.servicedriveruser.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mxln.innercommon.dto.DriverUserDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverMapper extends BaseMapper<DriverUserDTO> {

    Integer select1(@Param("cityCode") String cityCode);

}
