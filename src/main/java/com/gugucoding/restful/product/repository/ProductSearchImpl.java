package com.gugucoding.restful.product.repository;

import com.gugucoding.restful.product.dto.ProductListDTO;
import com.gugucoding.restful.product.entity.ProductEntity;
import com.gugucoding.restful.product.entity.QProductEntity;
import com.gugucoding.restful.product.entity.QProductImage;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;


public class ProductSearchImpl extends QuerydslRepositorySupport implements ProductSearch {
    public ProductSearchImpl(){
        super(ProductEntity.class);
    }


    @Override
    public Page<ProductListDTO> list(Pageable pageable) {

        QProductEntity productEntity = QProductEntity.productEntity;
        QProductImage productImage = QProductImage.productImage;

        JPQLQuery<ProductEntity> query = from(productEntity);
        query.leftJoin(productEntity.images,productImage);

        query.where(productImage.idx.eq(0));

        JPQLQuery<ProductListDTO> dtojpqlQuery = query.select(Projections.bean(
                ProductListDTO.class, productEntity.pno,
                productEntity.pname,
                productEntity.price,
                productEntity.writer,
                productImage.fileName.as("productImage")));


        this.getQuerydsl().applyPagination(pageable,dtojpqlQuery);

        List<ProductListDTO> dtoList = dtojpqlQuery.fetch();

        long count = dtojpqlQuery.fetchCount();

        return new PageImpl<>(dtoList,pageable,count);

    }
}
