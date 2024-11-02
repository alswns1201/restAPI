package com.gugucoding.restful.member.security.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

@Component
@Log4j2
public class JWTUtil {

    private static String key  ="34234234234234234";

    public String createToken(Map<String,Object> valueMap, int min){
        SecretKey key = null;

        try {
            key  = Keys.hmacShaKeyFor(JWTUtil.key.getBytes("UTF-8"));

        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }

        return Jwts.builder().header()
                .add("typ","JWT")
                .add("alg","HS256")
                .and()
                .issuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .expiration((Date.from(ZonedDateTime.now().plusMinutes(min).toInstant())))
                .claims(valueMap) // 페이로드에 받은 정보를 넣는다.
                .signWith(key)  // 위에서 만는 키로 서명 .
                .compact();
    }


    public Map<String,Object> validateToken(String token){
        SecretKey key = null;

        try {
            key = Keys.hmacShaKeyFor(JWTUtil.key.getBytes("UTF-8"));


        }catch (Exception e){
            throw  new RuntimeException(e.getMessage());
        }

        Claims claims = Jwts.parser().verifyWith(key).build().parseEncryptedClaims(token).getPayload();

        return claims;
    }



}
