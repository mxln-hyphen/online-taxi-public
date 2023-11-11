package com.mxln.servicessepush.controller;

import com.mxln.innercommon.Util.SsePrefixUtils;
import com.mxln.innercommon.dto.ResponseResult;
import com.mxln.innercommon.request.PushRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {

    public static Map<String, SseEmitter> sseEmitterMap = new HashMap<>();

    /**
     * 建立连接
     * @param userId 用户id
     * @param identity 身份类型
     * @return
     */
    @GetMapping("/connect")
    public SseEmitter connect(@RequestParam String userId, @RequestParam String identity){
        System.out.println("连接开始");
        SseEmitter sseEmitter = new SseEmitter(0l);

        String sseMapKey = SsePrefixUtils.generatorSseKey(userId,identity);

        sseEmitterMap.put(sseMapKey, sseEmitter);

        return sseEmitter;
    }



    /**
     * 发送消息
     * @param pushRequest
     * @return
     */
    @PostMapping("/push")
    public String push(@RequestBody PushRequest pushRequest){

        String userId = pushRequest.getUserId();
        String identity = pushRequest.getIdentity();
        String content = pushRequest.getContent();

        System.out.println(userId);
        System.out.println(identity);
        System.out.println(content);

        String sseMapKey = SsePrefixUtils.generatorSseKey(userId,identity);
        try {
            if (sseEmitterMap.containsKey(sseMapKey)){
                sseEmitterMap.get(sseMapKey).send(content);
            }else {
                return "推送失败";
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "给用户："+sseMapKey+",发送了消息："+content;
    }

    /**
     * 关闭连接
     * @param userId 用户ID
     * @param identity 身份类型
     * @return
     */
    @GetMapping("/close")
    public String close(@RequestParam String userId, @RequestParam String identity){
        String sseMapKey = SsePrefixUtils.generatorSseKey(userId,identity);
        System.out.println("关闭连接："+sseMapKey);
        if (sseEmitterMap.containsKey(sseMapKey)){
            sseEmitterMap.remove(sseMapKey);
        }
        return "close 成功";

    }

}
