package com.gugucoding.restful.product.service;

import com.gugucoding.restful.product.dto.PageRequestDTO;
import com.gugucoding.restful.product.dto.ProductDTO;
import com.gugucoding.restful.product.dto.ProductListDTO;
import com.gugucoding.restful.product.entity.ProductEntity;
import com.gugucoding.restful.product.exception.ProductExceptions;
import com.gugucoding.restful.product.repository.ProductRespository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRespository productRespository;

    public ProductDTO register(ProductDTO productDTO){
        try {
            log.info("resgister--------------");

            ProductEntity productEntity = productDTO.toEntity(); // dto를 실제 데이터로 적용 Entity 변환.

            productRespository.save(productEntity);// Entity를 저장.

            return new ProductDTO(productEntity); // Dto를 변환해서 client에 전송.

        }catch (Exception e){
            throw ProductExceptions.PRODUCT_NOT_REGISTERED.get();
        }
    }

    @Transactional(readOnly = true)
    public ProductDTO read(Long pno){
            log.info("read------------");
            Optional<ProductEntity> result = productRespository.getProduct(pno);
            ProductEntity productEntity = result.orElseThrow(ProductExceptions.PRODUCT_NOT_FOUND::get);
            return new ProductDTO(productEntity);
    }

    public void remove(Long pno){
        Optional<ProductEntity> result = productRespository.findById(pno);
        ProductEntity productEntity = result.orElseThrow(ProductExceptions.PRODUCT_NOT_FOUND::get);
        try {
            productRespository.delete(productEntity);
        }catch (Exception e){
            throw ProductExceptions.PRODUCT_NOT_REMOVE.get();
        }

    }


    public ProductDTO modify(ProductDTO productDTO){
        Optional<ProductEntity> result = productRespository.findById(productDTO.getPno());
        ProductEntity productEntity = result.orElseThrow(ProductExceptions.PRODUCT_NOT_FOUND::get);
        try {
          productEntity.changePrice(productDTO.getPrice());// 예시 상품 금액 수정.

            //이미지 추가
            List<String> fileNames = productDTO.getImageList();
            if(fileNames != null && !fileNames.isEmpty()){
                fileNames.forEach(productEntity::addImage);
            }

            productRespository.save(productEntity); // JPA에서는 SAVE로 UPDATE
            return new ProductDTO(productEntity);

        }catch (Exception e){
            throw ProductExceptions.PRODUCT_NOT_MODIFIED.get();
        }

    }


    public Page<ProductListDTO> getList(PageRequestDTO pageRequestDTO){
        try {
            Pageable pageable = pageRequestDTO.getPageable(Sort.by("pno").descending());
            return productRespository.list(pageable);
        }catch (Exception e){
            throw ProductExceptions.PRODUCT_NOT_FETCHED.get();
        }
    }



}
