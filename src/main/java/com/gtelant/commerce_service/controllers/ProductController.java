package com.gtelant.commerce_service.controllers;

import com.gtelant.commerce_service.dtos.ProductRequest;
import com.gtelant.commerce_service.dtos.ProductResponse;
import com.gtelant.commerce_service.dtos.UserRequest;
import com.gtelant.commerce_service.dtos.UserResponse;
import com.gtelant.commerce_service.mappers.ProductMapper;
import com.gtelant.commerce_service.models.Product;
import com.gtelant.commerce_service.models.User;
import com.gtelant.commerce_service.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductMapper productMapper;

    @Operation(summary = "Create a new product", description = "Creates a new product and returns the created product")
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest) {

        //todo 尚未填入Category 資訊 （已知 ProductRequest 存在 category_id ）
        Product product = productMapper.toProduct(productRequest);
        Product createdProduct = productService.createProduct(product);
        return ResponseEntity.ok(productMapper.toProductResponse(createdProduct));
    }
}
