package com.mxln.servicemap.controller;

import com.mxln.innercommon.dto.LocateDTO;
import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.request.TrackRequest;
import com.mxln.servicemap.Service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TrackController {

    @Autowired
    private TrackService trackService;

    @PostMapping("/track/terminal/add")
    public ResponseResult terminalAdd(@RequestBody TrackRequest trackRequest){

        return trackService.terminalAdd(trackRequest);

    }

    @PostMapping("/track/trace/add")
    public ResponseResult traceAdd(@RequestBody TrackRequest trackRequest){

        return trackService.traceAdd(trackRequest);
    }

    @PostMapping("/track/point/upload")
    public ResponseResult pointUpload(@RequestBody TrackRequest trackRequest){

        System.out.println(trackRequest.getKey());
        System.out.println(trackRequest.getPoints());


        return trackService.pointUpload(trackRequest);
    }

}
