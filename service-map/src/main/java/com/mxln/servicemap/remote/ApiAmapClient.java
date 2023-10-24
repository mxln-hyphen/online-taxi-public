package com.mxln.servicemap.remote;

import com.mxln.innercommon.constant.AmapConfigConstant;
import com.mxln.innercommon.dto.DistanceDTO;
import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.responses.DistanceResponse;
import lombok.extern.log4j.Log4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 高德地图Api
 */

@Service
public class ApiAmapClient {

    /**
     * 通过高德地图API获取路径距离和时长
     *
     * @return
     */

    @Autowired
    private RestTemplate restTemplate;

    public DistanceResponse getDistance(DistanceDTO distanceDTO) {
        //组装URL
        StringBuilder builder = new StringBuilder();
        builder.append(AmapConfigConstant.URL_PREFIX);
        builder.append("?");
        builder.append(AmapConfigConstant.URL_ORIGIN_PARAMETERS);
        builder.append("=");
        builder.append(distanceDTO.getDepLongitude());
        builder.append(",");
        builder.append(distanceDTO.getDepLatitude());
        builder.append("&");
        builder.append(AmapConfigConstant.URL_DESTINATION_PARAMETERS);
        builder.append("=");
        builder.append(distanceDTO.getDestLongitude());
        builder.append(",");
        builder.append(distanceDTO.getDestLatitude());
        builder.append("&");
        builder.append(AmapConfigConstant.URL_KEY_PARAMETERS);
        builder.append("=");
        builder.append(AmapConfigConstant.KEY_VALUE);
        System.out.println(builder.toString());

        //调用高德地图API
        ResponseEntity<String> forEntity = restTemplate.getForEntity(builder.toString(), String.class);

        //解析返回的JSON数据
        DistanceResponse distanceResponse = parseDirectionEntity(forEntity.getBody());

        //返回
        return distanceResponse;

    }

    private DistanceResponse parseDirectionEntity(String directionString) {
        DistanceResponse distanceResponse = null;
        try {
            distanceResponse = new DistanceResponse();
            JSONObject result = JSONObject.fromObject(directionString);
            if (result.getInt(AmapConfigConstant.JSON_STATUS_PARAMETERS) == 1) {
                if(result.has("route")){
                    JSONObject route = result.getJSONObject("route");
                    if(route.has("paths")){
                        JSONArray paths = route.getJSONArray("paths");
                        if(paths.getJSONObject(0).has("distance")&&paths.getJSONObject(0).has("duration")){
                            distanceResponse.setDistance(Integer.parseInt(paths.getJSONObject(0).getString("distance")));
                            distanceResponse.setDuration(Integer.parseInt(paths.getJSONObject(0).getString("duration")));
                            return distanceResponse;
                        }
                    }
                }
            }
        } catch (Exception e) {
            return null;
        }

        return null;
    }

}
