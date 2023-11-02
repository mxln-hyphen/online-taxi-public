package com.mxln.apidriver.service;

import com.mxln.apidriver.remote.ServiceDriverUserClient;
import com.mxln.apidriver.remote.ServiceMapClient;
import com.mxln.innercommon.dto.LocateDTO;
import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.request.CarInfoRequest;
import com.mxln.innercommon.request.PointUploadRequest;
import com.mxln.innercommon.request.TrackRequest;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TrackService {

    @Value("${Amap.key}")
    private String key;

    @Value("${Amap.sid}")
    private Integer sid;


    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;

    @Autowired
    private ServiceMapClient serviceMapClient;

    /**
     * 上传指定车辆轨迹信息
     * @param pointUploadRequest
     * @return
     */
    public ResponseResult pointUpload(PointUploadRequest pointUploadRequest){
        //根据车辆id获取车辆tid和trid
        CarInfoRequest carInfoRequest = new CarInfoRequest();
        carInfoRequest.setId(pointUploadRequest.getId());
        ResponseResult car = serviceDriverUserClient.getCar(carInfoRequest);
        JSONObject jsonObject = JSONObject.fromObject(car.getData());

        //生成TrackRequest请求类
        TrackRequest trackRequest = new TrackRequest();
        trackRequest.setKey(key);
        trackRequest.setSid(sid);
        trackRequest.setTid(jsonObject.getInt("tid"));
        trackRequest.setTrid(jsonObject.getInt("trid"));
        LocateDTO[] list = new LocateDTO[1];
        list[0] = new LocateDTO();
        list[0].setLocation(pointUploadRequest.getLocation());
        list[0].setLocatetime(String.valueOf(System.currentTimeMillis()));
        trackRequest.setPoints(list);

        //上传轨迹点信息
        serviceMapClient.pointUpload(trackRequest);

        //响应
        return ResponseResult.success();
    }
}
