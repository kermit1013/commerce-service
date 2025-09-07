package com.gtelant.commerce_service.mappers;

import com.gtelant.commerce_service.dtos.ProductRequest;
import com.gtelant.commerce_service.dtos.ProductResponse;
import com.gtelant.commerce_service.models.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductResponse toProductResponse(Product product) {
        ProductResponse dto = new ProductResponse();
        dto.setId(product.getId());
        dto.setReference(product.getReference());
        dto.setDescription(product.getDescription());
        dto.setHeight(product.getHeight());
        dto.setPrice(product.getPrice());
        return dto;
    }

    public Product toProduct(ProductRequest dto) {
        Product product = new Product();
        product.setReference(dto.getReference());
        product.setDescription(dto.getDescription());
        product.setImageUrl(dto.getImageUrl());
        product.setThumbnailUrl(dto.getThumbnailUrl());
        product.setHeight(dto.getHeight());
        product.setPrice(dto.getPrice());
        product.setSales(dto.getSales());
        product.setStock(dto.getStock());
        product.setWidth(dto.getWidth());

        return product;
    }
}
