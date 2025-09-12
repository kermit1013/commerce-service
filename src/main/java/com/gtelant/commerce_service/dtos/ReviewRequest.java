package com.gtelant.commerce_service.dtos;

import com.gtelant.commerce_service.enums.ReviewStatus;
import lombok.Data;


@Data
public class ReviewRequest {
    private int userId;

    private int productId;

    private int rating;

    private String comment;
}
