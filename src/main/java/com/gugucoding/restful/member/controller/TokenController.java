package com.gugucoding.restful.member.controller;


import com.gugucoding.restful.member.dto.MemberDTO;
import com.gugucoding.restful.member.security.util.JWTUtil;
import com.gugucoding.restful.member.service.MemberService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.proxy.map.MapProxy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/token")
@Log4j2
@RequiredArgsConstructor
public class TokenController {

    private final MemberService memberService;

    private final JWTUtil jwtUtil;

    @PostMapping("/make")
    public ResponseEntity<Map<String,String>> makeToken(@RequestBody MemberDTO memberDTO){
        log.info(".................make token...............");

        MemberDTO result  = memberService.read(memberDTO.getMid(),memberDTO.getMpw());
        log.info(result);

        String mid = result.getMid();
        Map<String,Object> dataMap = result.getDataMap();
        String accessToken =  jwtUtil.createToken(dataMap,10);
        String refreshToken = jwtUtil.createToken(Map.of("mid",mid),60*24*7);


        log.info("accessToken : " + accessToken);
        log.info("refreshToken : "+ refreshToken);

        return ResponseEntity.ok(Map.of("accessToken",accessToken, "refreshToken",refreshToken));

    }

    @PostMapping("/refresh")
    public ResponseEntity<Map<String,String>> refreshToken(
            @RequestHeader("Authorization") String accessTokenStr,
            @RequestParam("refreshToken") String refreshToken,
            @RequestParam("mid") String mid
    ){
        // 예외 처리
        log.info("---------Exception doing-----------");
        if(accessTokenStr == null || !accessTokenStr.startsWith("Bearer ")){
            return handleException("No Access Token",400);
        }

        if(refreshToken == null){
            return handleException("No Refresh Token",400);
        }
        log.info("---------refreshToken : " + refreshToken);

        if(mid == null){
            return handleException("No Mid",400);
        }

        // Access Token 만료 확인
        log.info("---------Access Token confirm-----------");
        String accessToken = accessTokenStr.substring(7);
        try {
            jwtUtil.validateToken(accessToken);
            //만료가 남아 있다면
            Map<String ,String> data = makeData(mid,accessToken,refreshToken);
            return ResponseEntity.ok(data);
        }catch (ExpiredJwtException expiredJwtException){
            try {
                //새로운 토근 발급 필요 (만료)
                Map<String,String> newTokenMap =  makeNewData(mid,refreshToken);
                return ResponseEntity.ok(newTokenMap);
            }catch (Exception e){
                return handleException("Refresh Error"+e.getMessage(),400);
            }


        }catch (Exception e){
            return handleException(e.getMessage(),400);
        }

        // Refresh Token 검증

        // Refresh Token 에서 mid 값 추출

        // 전송
    }
    private Map<String,String> makeNewData(String mid, String refreshToken){
        try {
            Map<String,Object> claims = jwtUtil.validateToken(refreshToken);
            if(!mid.equals(claims.get("mid").toString())){
                throw new RuntimeException("Invalid Refresh Token Host");
            }

            MemberDTO memberDTO = memberService.getByMid(mid);

            Map<String,Object> newClaims = memberDTO.getDataMap();
            String newAccessToken = jwtUtil.createToken(newClaims,10);
            String newRefreshToken = jwtUtil.createToken(Map.of("mid",mid),60*24*7);
            return makeData(mid,newAccessToken,newRefreshToken);

        }catch (Exception e){
                handleException(e.getMessage(),400);
        }
        return null;
    }

    private Map<String,String> makeData(String mid, String accessToken, String refreshToken){
        return Map.of("mid",mid,"accessToken",accessToken,"refreshToken",refreshToken);
    }

    private ResponseEntity<Map<String,String>> handleException(String msg, int status){
        return ResponseEntity.status(status).body(Map.of("error",msg));
    }



}
