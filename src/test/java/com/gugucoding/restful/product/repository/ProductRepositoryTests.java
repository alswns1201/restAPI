package com.gugucoding.restful.product.repository;

import com.gugucoding.restful.product.dto.ProductListDTO;
import com.gugucoding.restful.product.entity.ProductEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class ProductRepositoryTests {

    @Autowired
    private ProductRespository productRespository;

    @Test
    public void querydslTestLIST(){
        Pageable pageable = PageRequest.of(0,10, Sort.by("pno").descending());
        Page<ProductListDTO> result = productRespository.list(pageable);


        result.getContent().forEach(productListDTO -> System.out.println(productListDTO));

    }


    @Test
    @Transactional
    @Commit
    public void testInsert(){
        for(int i = 1; i <= 50; i++){
            ProductEntity productEntity = ProductEntity.builder()
                    .pname(i+"_새로운 상품")
                    .price(1000)
                    .content(i+"+상품 내용")
                    .writer("user")
                    .build();

            productEntity.addImage(i+"_test1.jpg");
            productEntity.addImage(i+"_test2.jpg");

            productRespository.save(productEntity);
        }
    }


}
