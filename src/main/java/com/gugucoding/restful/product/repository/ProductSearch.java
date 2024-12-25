package com.gugucoding.restful.product.repository;

import com.gugucoding.restful.product.dto.ProductListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductSearch {

    Page<ProductListDTO> list(Pageable pageable);

}
