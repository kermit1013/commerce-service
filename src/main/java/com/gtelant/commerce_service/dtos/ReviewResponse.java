package com.gtelant.commerce_service.dtos;

import com.gtelant.commerce_service.enums.ReviewStatus;
import com.gtelant.commerce_service.models.Product;
import com.gtelant.commerce_service.models.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewResponse {
    private Integer id;
    private UserResponse user;
    private ProductResponse product;
    private int rating;
    private String comment;
    private ReviewStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime deleteAt;
}
