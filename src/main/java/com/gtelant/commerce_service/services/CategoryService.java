package com.gtelant.commerce_service.services;

import com.gtelant.commerce_service.models.Category;
import com.gtelant.commerce_service.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category updateCategory(int id, Category category) {
        if (categoryRepository.existsById(id)) {
            category.setId(id);
            return categoryRepository.save(category);
        }
        return null;
    }

    public Category findById(int id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        return categoryOptional.orElseThrow(() -> new RuntimeException("category not found"));
    }
}
