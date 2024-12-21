package com.gugucoding.restful.member.exeption;


import com.gugucoding.restful.member.entity.MemberEntity;
import com.gugucoding.restful.member.exception.MemberExceptions;
import com.gugucoding.restful.member.repository.MemberRepository;
import com.gugucoding.restful.product.dto.ProductDTO;
import com.gugucoding.restful.product.entity.ProductEntity;
import com.gugucoding.restful.product.repository.ProductRespository;
import org.hibernate.query.sql.internal.ParameterRecognizerImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
public class MemberExceptionTests {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProductRespository productRespository;

    @Test
    public void testRead(){
        String mid = "user1000";
        Optional<MemberEntity> result = memberRepository.findById(mid);

        MemberEntity memberEntity = result.orElseThrow(MemberExceptions.NOT_FOUND::get);

        System.out.println(result);
    }

    @Test
    public void testQueryRead(){
        Long pno = 1L;
        Optional<ProductEntity> result = productRespository.getProduct(pno);

        ProductEntity productEntity = result.get();

        System.out.println(result);

    }


    @Test
    @Transactional
    @Commit
    public void TestUpdate(){
        Optional<ProductEntity> result = productRespository.getProduct(1l);
        ProductEntity productEntity = result.get();
        productEntity.changeTitle("변경");
        productEntity.addImage("new1.jpg");
    }

    @Test
    public void DTOT테스트Image(){
        Long pno = 1L;
        Optional<ProductDTO> result = productRespository.getProductDTO(pno);
        ProductDTO productDTO = result.get();
        System.out.println(productDTO);
    }


}
