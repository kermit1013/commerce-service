package com.gtelant.commerce_service.dtos;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductRequest {
    private String imageUrl;

    private String thumbnailUrl;

    private String reference;

    private double width;

    private double height;

    private BigDecimal price;

    private int stock;

    private int sales;

    private String description;

    private int categoryId;
}
