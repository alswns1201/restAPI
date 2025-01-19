package com.gugucoding.restful.product.controller;

import com.gugucoding.restful.product.dto.PageRequestDTO;
import com.gugucoding.restful.product.dto.ProductListDTO;
import com.gugucoding.restful.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/lists")
    public ResponseEntity<Page<ProductListDTO>> list(@Validated PageRequestDTO pageRequestDTO,
                                                     Principal principal){
        return ResponseEntity.ok(productService.getList(pageRequestDTO));
    }



}
