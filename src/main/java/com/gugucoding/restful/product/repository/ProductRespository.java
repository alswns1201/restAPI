package com.gugucoding.restful.product.repository;


import com.gugucoding.restful.product.dto.ProductDTO;
import com.gugucoding.restful.product.entity.ProductEntity;
import com.gugucoding.restful.product.entity.ProductImage;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRespository extends JpaRepository<ProductEntity,Long> , ProductSearch{

    @EntityGraph(attributePaths = {"images"},type = EntityGraph.EntityGraphType.FETCH)
    @Query("select  p from ProductEntity p where p.pno = :pno")
    Optional<ProductEntity> getProduct(@Param("pno") Long pno);

    @Query("select p from ProductEntity p join fetch p.images pi where p.pno = :pno")
    Optional<ProductDTO> getProductDTO(@Param("pno") Long pno);


}
