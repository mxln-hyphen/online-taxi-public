package com.mxln.servicemap.controller;

import com.mxln.innercommon.dto.LocateDTO;
import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.request.TrackRequest;
import com.mxln.servicemap.Service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

        return trackService.pointUpload(trackRequest);
    }

    @PostMapping("/track/terminal/aroundsearch")
    public ResponseResult aroundSearch(@RequestBody TrackRequest trackRequest){


        return trackService.aroundSearch(trackRequest);
    }

    @GetMapping("/track/trace/search")
    public ResponseResult traceSearch(@RequestParam Map<String,String> parammap){
        TrackRequest trackRequest = new TrackRequest();
        trackRequest.setKey(parammap.get("key"));
        trackRequest.setSid(Integer.parseInt(parammap.get("sid")));
        trackRequest.setTid(Integer.parseInt(parammap.get("tid")));
        trackRequest.setStarttime(parammap.get("starttime"));
        trackRequest.setEndtime(parammap.get("endtime"));

        System.out.println(trackRequest.getKey());
        System.out.println(trackRequest.getSid());
        System.out.println(trackRequest.getTid());
        System.out.println(trackRequest.getEndtime());
        System.out.println(trackRequest.getEndtime());

        return trackService.traceSearch(trackRequest);
    }

}
