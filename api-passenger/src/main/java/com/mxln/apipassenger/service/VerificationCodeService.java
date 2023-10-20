package com.mxln.apipassenger.service;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class VerificationCodeService {

    /**
     * 调用验证码服务并存入redis
     * @return
     */
    public String get(String pessengerPhoneNumber){
        String verificationCode;

        //调用验证码服务
        verificationCode = "VerCode";
        System.out.println("调用验证码服务");

        //验证码存入redis
        System.out.println("验证码存入redis");

        //返回值
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",1);
        jsonObject.put("message",verificationCode);


        return jsonObject.toString();
    }

}
