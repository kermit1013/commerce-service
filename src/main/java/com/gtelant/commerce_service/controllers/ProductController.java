package com.gtelant.commerce_service.controllers;

import com.gtelant.commerce_service.dtos.ProductRequest;
import com.gtelant.commerce_service.dtos.ProductResponse;
import com.gtelant.commerce_service.mappers.ProductMapper;
import com.gtelant.commerce_service.models.Product;
import com.gtelant.commerce_service.models.Category;
import com.gtelant.commerce_service.services.CategoryService;
import com.gtelant.commerce_service.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CategoryService categoryService;

    @Operation(summary = "Create a new product", description = "Creates a new product and returns the created product")
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest) {
        Category category = categoryService.findById(productRequest.getCategoryId());
        Product product = productMapper.toProduct(productRequest, category);
        Product createdProduct = productService.createProduct(product);
        return ResponseEntity.ok(productMapper.toProductResponse(createdProduct));
    }
}
