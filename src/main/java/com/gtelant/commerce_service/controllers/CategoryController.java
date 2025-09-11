package com.gtelant.commerce_service.controllers;

import com.gtelant.commerce_service.dtos.*;
import com.gtelant.commerce_service.mappers.CategoryMapper;
import com.gtelant.commerce_service.mappers.ProductMapper;
import com.gtelant.commerce_service.models.Category;
import com.gtelant.commerce_service.models.User;
import com.gtelant.commerce_service.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@Tag(name = "Category", description = "Category management APIs")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ProductMapper productMapper;

    @Operation(summary = "Get all categories", description = "Returns a list of all categories")
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
       List<CategoryResponse> responses= categoryService.getAllCategories().stream()
                .map(category -> {
                    CategoryResponse response = categoryMapper.toCategoryResponse(category);
                    List<CategoryProductResponse> productResponseList = category
                            .getProductList()
                            .stream()
                            .map(product -> productMapper.toCategoryProductResponse(product)).toList();
                    response.setProductList(productResponseList);
                    return response;
                })
                .toList();

        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable int id, @RequestBody CategoryRequest categoryRequest) {
        Category category = categoryMapper.toCategory(categoryRequest);
        Category updatedCategory = categoryService.updateCategory(id, category);
        if (updatedCategory == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categoryMapper.toCategoryResponse(updatedCategory));
    }
}
