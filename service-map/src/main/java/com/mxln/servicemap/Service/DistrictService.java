package com.mxln.servicemap.Service;

import com.mxln.innercommon.dto.DistrictDTO;
import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.servicemap.mapper.DistrictMapper;
import com.mxln.servicemap.remote.ApiAmapClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class DistrictService {

    @Autowired
    private ApiAmapClient apiAmapClient;

    @Autowired
    private DistrictMapper districtMapper;

    /**
     * 初始化区域信息数据
     * @return
     */
    public ResponseResult initDistrict(){

        //访问高德地图API，获取区域信息
        ArrayList<DistrictDTO> arrayList = apiAmapClient.getDistrict();

        //将区域信息写入数据库
        for (DistrictDTO districtDTO : arrayList) {
            districtMapper.insert(districtDTO);
        }

        //响应
        return ResponseResult.success();

    }

}
