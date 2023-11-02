package com.mxln.apidriver.controller;

import com.mxln.apidriver.service.TrackService;
import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.request.PointUploadRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TrackController {

    @Autowired
    private TrackService trackService;

    @PostMapping("/point-upload")
    public ResponseResult pointUpload(@RequestBody PointUploadRequest pointUploadRequest){
        System.out.println(pointUploadRequest.getId());
        System.out.println(pointUploadRequest.getLocation());
        trackService.pointUpload(pointUploadRequest);

        return ResponseResult.success();
    }

}
