package com.gugucoding.restful.product.repository;

import com.gugucoding.restful.product.dto.ProductDTO;
import com.gugucoding.restful.product.dto.ProductListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductSearch {

    Page<ProductListDTO> list(Pageable pageable);

    Page<ProductDTO> listWithAllImages(Pageable pageable);

    //N+1 문제 패치조인 이용
    Page<ProductDTO> listFetchAllImages(Pageable pageable);


}
