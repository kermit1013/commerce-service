package com.gtelant.commerce_service.services;

import com.gtelant.commerce_service.enums.ReviewStatus;
import com.gtelant.commerce_service.models.Review;
import com.gtelant.commerce_service.models.User;
import com.gtelant.commerce_service.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    public Review getReviewById(int id) {
        Optional<Review> reviewOptional = reviewRepository.findById(id);

        return reviewOptional.orElseThrow(()-> new RuntimeException("review not found!"));
    }

    public List<Review> getReviewsByIds(List<Integer> ids) {
        return reviewRepository.findAllById(ids);
    }


    public Review createReview(Review review) {
        return reviewRepository.save(review);
    }

    public Review updateReview(Review review) {
        return reviewRepository.save(review);
    }

    public List<Review> updateReviews(List<Review> reviews) {
        return reviewRepository.saveAll(reviews); //2
    }
}
