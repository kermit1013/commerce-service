package com.gtelant.commerce_service.services;

import com.gtelant.commerce_service.models.Product;
import com.gtelant.commerce_service.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }
}
