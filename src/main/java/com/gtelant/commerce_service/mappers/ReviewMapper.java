package com.gtelant.commerce_service.mappers;

import com.gtelant.commerce_service.dtos.ReviewRequest;
import com.gtelant.commerce_service.dtos.ReviewResponse;
import com.gtelant.commerce_service.models.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {
    public Review toReview(ReviewRequest request) {
        Review review = new Review();
        review.setRating(request.getRating());
        review.setComment(request.getComment());
        return review;
    }

    public ReviewResponse toReviewResponse(Review review) {
        ReviewResponse response = new ReviewResponse();
        response.setId(review.getId());
        response.setRating(review.getRating());
        response.setStatus(review.getStatus());
        response.setComment(review.getComment());
        response.setCreatedAt(review.getCreatedAt());
        response.setDeleteAt(review.getDeleteAt());
        return response;
    }
}
