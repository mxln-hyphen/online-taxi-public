package com.mxln.servicemap.remote;

import com.mxln.innercommon.constant.AmapLieyingConfigConstant;
import com.mxln.innercommon.dto.LocateDTO;
import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.request.TrackRequest;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class TrackClient {

    @Autowired
    private RestTemplate restTemplate;


    /**
     * 调用高德地图猎鹰API创建终端
     * @param trackRequest
     * @return
     */
    public TrackRequest terminalAdd(TrackRequest trackRequest){
        //组装url
        StringBuilder builder = new StringBuilder();
        builder.append(AmapLieyingConfigConstant.TERMINAL_ADD_URL);
        builder.append("?");
        builder.append("key");
        builder.append("=");
        builder.append(trackRequest.getKey());
        builder.append("&");
        builder.append("sid");
        builder.append("=");
        builder.append(trackRequest.getSid());
        builder.append("&");
        builder.append("name");
        builder.append("=");
        builder.append(trackRequest.getName());

        //调用高德地图猎鹰API
        ResponseEntity<String> forEntity = restTemplate.postForEntity(builder.toString(), null,String.class);
        JSONObject jsonObject = JSONObject.fromObject(forEntity.getBody());

        //将响应的tid加入返回值
        try {
            JSONObject data = jsonObject.getJSONObject("data");
            trackRequest.setTid(data.getInt("tid"));
        }catch (Exception e){

        }

        //响应
        return trackRequest;
    }

    /**
     * 调用高德地图猎鹰API创建轨迹
     * @param trackRequest
     * @return
     */
    public TrackRequest traceAdd(TrackRequest trackRequest){
        //组装url
        StringBuilder builder = new StringBuilder();
        builder.append(AmapLieyingConfigConstant.TRACE_ADD_URL);
        builder.append("?");
        builder.append("key");
        builder.append("=");
        builder.append(trackRequest.getKey());
        builder.append("&");
        builder.append("sid");
        builder.append("=");
        builder.append(trackRequest.getSid());
        builder.append("&");
        builder.append("tid");
        builder.append("=");
        builder.append(trackRequest.getTid());
        builder.append("&");
        builder.append("trname");
        builder.append("=");
        builder.append(trackRequest.getTrname());

        //调用高德地图猎鹰API
        ResponseEntity<String> forEntity = restTemplate.postForEntity(builder.toString(), null,String.class);
        JSONObject jsonObject = JSONObject.fromObject(forEntity.getBody());

        //将响应的tid加入返回值
        try {
            JSONObject data = jsonObject.getJSONObject("data");
            trackRequest.setTrid(data.getInt("trid"));
        }catch (Exception e){

        }

        //响应
        return trackRequest;
    }


    /**
     * 调用高德地图猎鹰API进行轨迹点上传
     * @param trackRequest
     * @return
     */
    public String pointUpload(TrackRequest trackRequest){
        //组装URL
        HttpHeaders headers = new HttpHeaders();headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>(){{
                add("key",trackRequest.getKey());
                add("sid",trackRequest.getSid());
                add("tid",trackRequest.getTid());
                add("trid",trackRequest.getTrid());
                add("points",JSONArray.fromObject(trackRequest.getPoints()));
        }};
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(map, headers);

        //调用高德地图猎鹰API
        ResponseEntity<String> forEntity = restTemplate.postForEntity(AmapLieyingConfigConstant.POINT_UPLOAD_URL
                , request,String.class);

        //响应
        return forEntity.getBody();
    }

}
