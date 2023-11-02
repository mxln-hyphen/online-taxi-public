package com.mxln.servicemap.Service;

import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.request.TrackRequest;
import com.mxln.servicemap.remote.TrackClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrackService {

    @Autowired
    private TrackClient trackClient;


    /**
     * 创建终端
     * @return
     */
    public ResponseResult<TrackRequest> terminalAdd(TrackRequest trackRequest){
        //调用高德地图猎鹰API创建终端
        trackRequest = trackClient.terminalAdd(trackRequest);

        //响应
        return ResponseResult.success(trackRequest);
    }

    /**
     * 创建轨迹
     * @param trackRequest
     * @return
     */
    public ResponseResult<TrackRequest> traceAdd(TrackRequest trackRequest){
        //调用高德地图猎鹰API创建轨迹
        trackRequest = trackClient.traceAdd(trackRequest);

        //响应
        return ResponseResult.success(trackRequest);
    }

    /**
     * 轨迹点上传
     * @param trackRequest
     * @return
     */
    public ResponseResult<TrackRequest> pointUpload(TrackRequest trackRequest){
        //调用高德地图猎鹰API进行轨迹点上传
        String errorMessage = trackClient.pointUpload(trackRequest);
        System.out.println(errorMessage);

        //响应
        return ResponseResult.success();
    }
}
