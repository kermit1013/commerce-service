package com.gtelant.commerce_service.mappers;

import com.gtelant.commerce_service.dtos.*;
import com.gtelant.commerce_service.models.Category;
import com.gtelant.commerce_service.models.User;
import com.gtelant.commerce_service.models.UserSegment;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CategoryMapper {
    public CategoryResponse toCategoryResponse(Category category) {
        CategoryResponse dto = new CategoryResponse();
        dto.setId(category.getId());
        dto.setName(category.getName());


//        if (category.getProductList() != null) {
//            dto.setProductList(category.getProductList().stream()
//                    .map(this::toUserSegmentResponse)
//                    .collect(Collectors.toList()));
//        }
        return dto;
    }

    public Category toCategory(CategoryRequest dto) {
        Category category = new Category();
        category.setName(dto.getName());

        return category;
    }
}
