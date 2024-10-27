package com.gugucoding.restful.member.exeption;


import com.gugucoding.restful.member.entity.MemberEntity;
import com.gugucoding.restful.member.exception.MemberExceptions;
import com.gugucoding.restful.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class MemberExceptionTests {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void testRead(){
        String mid = "user1000";
        Optional<MemberEntity> result = memberRepository.findById(mid);

        MemberEntity memberEntity = result.orElseThrow(MemberExceptions.NOT_FOUND::get);

        System.out.println(result);
    }


}
