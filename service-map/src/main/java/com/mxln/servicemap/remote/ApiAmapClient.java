package com.mxln.servicemap.remote;

import com.mxln.innercommon.constant.AmapConfigConstant;
import com.mxln.innercommon.dto.DistanceDTO;
import com.mxln.innercommon.dto.DistrictDTO;
import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.responses.DistanceResponse;
import lombok.extern.log4j.Log4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

/**
 * 高德地图Api
 */

@Service
public class ApiAmapClient {


    @Autowired
    private RestTemplate restTemplate;

    /**
     * 通过高德地图API获取路径距离和时长
     *
     * @return
     */
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

    /**
     * 解析路径距离和时长信息返回值
     *
     * @param directionString
     * @return
     */
    private DistanceResponse parseDirectionEntity(String directionString) {
        DistanceResponse distanceResponse = null;
        try {
            distanceResponse = new DistanceResponse();
            JSONObject result = JSONObject.fromObject(directionString);
            if (result.getInt(AmapConfigConstant.JSON_STATUS_PARAMETERS) == 1) {
                if (result.has("route")) {
                    JSONObject route = result.getJSONObject("route");
                    if (route.has("paths")) {
                        JSONArray paths = route.getJSONArray("paths");
                        if (paths.getJSONObject(0).has("distance") && paths.getJSONObject(0).has("duration")) {
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

    /**
     * 调用高德地图API获取区域信息
     *
     * @return
     */
    public ArrayList<DistrictDTO> getDistrict() {
        //组装URL
        StringBuilder builder = new StringBuilder();
        builder.append(AmapConfigConstant.DISTRICT_URL_PREFIX);
        builder.append("?");
        builder.append(AmapConfigConstant.DISTRICT_URL_KEYWORDS_PARAMETERS);
        builder.append("=");
        builder.append("中国");
        builder.append("&");
        builder.append(AmapConfigConstant.DISTRICT_URL_SUBDISTRICT_PARAMETERS);
        builder.append("=");
        builder.append("3");
        builder.append("&");
        builder.append(AmapConfigConstant.DISTRICT_URL_KEY_PARAMETERS);
        builder.append("=");
        builder.append(AmapConfigConstant.KEY_VALUE);
        System.out.println(builder.toString());

        //调用高德地图API
        ResponseEntity<String> forEntity = restTemplate.getForEntity(builder.toString(), String.class);

        //解析JSON格式的区域信息
        ArrayList<DistrictDTO> arrayList = parseDistrictJSON(forEntity.getBody());

        //响应
        return arrayList;
    }


    /**
     * 解析JSON格式区域信息
     */
    private ArrayList<DistrictDTO> parseDistrictJSON(String district) {
        ArrayList<DistrictDTO> arrayList = new ArrayList<>();
        try {
            JSONObject jsonObject = JSONObject.fromObject(district);
            JSONArray jsonArray = jsonObject.getJSONArray("districts");
            //递归解析JSON数据
            if (jsonArray.size() != 0) {
                for (int i = 0; i < jsonArray.size(); i++) {
                    //递归解析
                    JSONObject object = jsonArray.getJSONObject(i);
                    String parent = "";
                    recursiveParseDistrictJSON(object, arrayList, parent);
                }
            }
        } catch (Exception e) {

        }
        return arrayList;
    }


    /**
     * 递归解析JSON格式区域信息
     *
     * @param jsonObject
     * @param arrayList
     * @param parentLevel 父地区代号
     */
    private void recursiveParseDistrictJSON(JSONObject jsonObject, ArrayList<DistrictDTO> arrayList, String parentLevel) {
        //解析当前级别
        try {
            DistrictDTO districtDTO = new DistrictDTO();
            districtDTO.setAddressCode(jsonObject.getString("adcode"));
            districtDTO.setAddressName(jsonObject.getString("name"));
            districtDTO.setLevel(parseLevel(jsonObject.getString("level")));
            districtDTO.setParentAddressCode(parentLevel);
            if (districtDTO.getLevel() != -1)
                arrayList.add(districtDTO);
        } catch (Exception e) {

        }

        //如果districts字段不为空，解析所有districts字段
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("districts");
            if (jsonArray.size() != 0) {
                for (int i = 0; i < jsonArray.size(); i++) {
                    //递归解析
                    JSONObject object = jsonArray.getJSONObject(i);
                    String parent = jsonObject.getString("adcode");

                    recursiveParseDistrictJSON(object, arrayList, parent);
                }
            }
        } catch (Exception e) {
        }
    }

    /**
     * 解析区域等级并返回数字
     *
     * @param level
     * @return
     */
    private int parseLevel(String level) {
        if (level.trim().equals("district")) {
            return 3;
        } else if (level.trim().equals("city")) {
            return 2;
        } else if (level.trim().equals("province")) {
            return 1;
        } else if (level.trim().equals("country")) {
            return 0;
        }
        return -1;
    }
}
