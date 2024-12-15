package com.gugucoding.restful.product.repository;


import com.gugucoding.restful.product.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRespository extends JpaRepository<ProductEntity,Long> {

}
