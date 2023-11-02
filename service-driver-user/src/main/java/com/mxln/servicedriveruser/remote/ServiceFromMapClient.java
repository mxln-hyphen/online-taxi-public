package com.mxln.servicedriveruser.remote;

import com.mxln.innercommon.constant.AmapLieyingConfigConstant;
import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.request.ServiceFromMapRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ServiceFromMapClient {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 调用高德地图猎鹰服务api创建服务
     * @return
     */
    public ResponseResult serviceAdd(ServiceFromMapRequest serviceFromMapRequest){
        //组装URL和请求参数
        StringBuilder builder = new StringBuilder();
        builder.append(AmapLieyingConfigConstant.SERVICE_ADD_URL);
        builder.append("?");
        builder.append("key");
        builder.append("=");
        //builder.append("\"");
        builder.append(AmapLieyingConfigConstant.KEY_VALUE);
        //builder.append("\"");
        builder.append("&");
        builder.append("name");
        builder.append("=");
        //builder.append("\"");
        builder.append(serviceFromMapRequest.getName());
        //builder.append("\"");

        //访问高德地图猎鹰服务api
        ResponseEntity<String> forEntity = restTemplate.postForEntity(builder.toString(), null,String.class);
        System.out.println(forEntity.getBody());

        //响应
        return ResponseResult.success();
    }
}
