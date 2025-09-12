package com.gtelant.commerce_service.dtos;

import com.gtelant.commerce_service.enums.ReviewStatus;
import lombok.Data;

import java.util.List;

@Data
public class UpdateReviewStatusRequest {
    private List<Integer> ids;
    private ReviewStatus reviewStatus;
}
