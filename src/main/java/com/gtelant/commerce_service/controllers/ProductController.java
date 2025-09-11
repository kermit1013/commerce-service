package com.gtelant.commerce_service.controllers;

import com.gtelant.commerce_service.dtos.ProductRequest;
import com.gtelant.commerce_service.dtos.ProductResponse;
import com.gtelant.commerce_service.dtos.UserResponse;
import com.gtelant.commerce_service.mappers.CategoryMapper;
import com.gtelant.commerce_service.mappers.ProductMapper;
import com.gtelant.commerce_service.models.Product;
import com.gtelant.commerce_service.models.Category;
import com.gtelant.commerce_service.services.CategoryService;
import com.gtelant.commerce_service.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryMapper categoryMapper;

    @Operation(summary = "Create a new product", description = "Creates a new product and returns the created product")
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest) {
        Category category = categoryService.findById(productRequest.getCategoryId());
        Product product = productMapper.toProduct(productRequest, category);
        Product createdProduct = productService.createProduct(product);

        ProductResponse response= productMapper.toProductResponse(createdProduct);
        response.setCategory(categoryMapper.toCategoryResponse(createdProduct.getCategory()));
        return ResponseEntity.ok(response);
    }


    @Operation(summary = "Get all product pagination", description = "Returns a page of products")
    @GetMapping("/page")
    public Page<ProductResponse> getAllProductsPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String query,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) Integer stockFrom,
            @RequestParam(required = false) Integer stockTo
    ) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return productService.getAllProduct(query, categoryId, stockFrom, stockTo, pageRequest)
                .map(productMapper::toProductResponse);
    }
}
