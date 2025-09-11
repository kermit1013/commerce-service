package com.gtelant.commerce_service.mappers;

import com.gtelant.commerce_service.dtos.*;
import com.gtelant.commerce_service.models.Category;
import org.springframework.stereotype.Component;



@Component
public class CategoryMapper {
    public CategoryResponse toCategoryResponse(Category category) {
        CategoryResponse dto = new CategoryResponse();
        dto.setId(category.getId());
        dto.setName(category.getName());
        return dto;
    }

    public Category toCategory(CategoryRequest dto) {
        Category category = new Category();
        category.setName(dto.getName());

        return category;
    }

}
