package com.gugucoding.restful.member.repository;


import com.gugucoding.restful.member.entity.MemberEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class MemberRepositoryTests {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private  MemberRepository memberRepository;

    @Test
    public void testInsert(){
        for(int i = 0 ; i<= 100; i++){
            MemberEntity user = MemberEntity.builder().
                    mid("user"+i)
                    .mpw(passwordEncoder.encode("1111"))
                    .mname("user"+i)
                    .email("user"+i+"@naver.com")
                    .role(i<=80 ? "user" : "admin")
                    .build();
            memberRepository.save(user);
        }
    }

}
