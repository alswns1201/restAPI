package com.gugucoding.restful.member.controller;


import com.gugucoding.restful.member.dto.MemberDTO;
import com.gugucoding.restful.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/token")
@Log4j2
@RequiredArgsConstructor
public class TokenController {

    private final MemberService memberService;

    @PostMapping("/make")
    public ResponseEntity<Map<String,String>> makeToken(@RequestBody MemberDTO memberDTO){
        log.info(".................make token...............");

        MemberDTO result  = memberService.read(memberDTO.getMid(),memberDTO.getMpw());
        log.info(result);

        return null;

    }


}
