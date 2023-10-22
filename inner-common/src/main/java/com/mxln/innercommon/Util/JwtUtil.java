package com.mxln.innercommon.Util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mxln.innercommon.dto.TokenResult;
import com.mxln.innercommon.request.JWTDTO;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {

    //密钥
    private final String SIGN = "eyJhbGciOiJ";

    //jwt字段名称
    private final String IDENTITY = "identity";
    private final String PHONE = "phone";
    private final String JWTTYPE = "type";
    private final String EXPIRATION = "expiration";


    /**
     * 根据乘客信息生成JWT
     *
     * @param jwtdto
     * @param JWTType
     * @return
     */
    public String generateJWT(JWTDTO jwtdto, String JWTType) {
        //用户信息
        Map<String, String> map = new HashMap<>();
        map.put(IDENTITY, String.valueOf(jwtdto.getIdentity()));
        map.put(PHONE, jwtdto.getPhone());

        //token过期时间和类别
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 30);//30分钟过期
        Date date = calendar.getTime();
        map.put(JWTTYPE, JWTType);

        //生成JWT
        JWTCreator.Builder builder = JWT.create();
        builder.withClaim("expiration", date);
        map.forEach(
                (k, v) -> {
                    builder.withClaim(k, v);
                }
        );

        String sign = builder.sign(Algorithm.HMAC256(SIGN));

        return sign;
    }

    /**
     * 根据JWT还原乘客信息
     *
     * @param token
     * @return
     */
    public TokenResult decode(String token) {
        //解析
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
        TokenResult tokenResult = new TokenResult();
        tokenResult.setIdentity(Integer.parseInt(verify.getClaim(IDENTITY).asString()));
        tokenResult.setPhone(verify.getClaim(PHONE).asString());
        tokenResult.setType(verify.getClaim(JWTTYPE).asString());
        return tokenResult;
    }





}
