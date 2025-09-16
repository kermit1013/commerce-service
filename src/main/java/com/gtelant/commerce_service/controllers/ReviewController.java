package com.gtelant.commerce_service.controllers;

import com.gtelant.commerce_service.dtos.ReviewRequest;
import com.gtelant.commerce_service.dtos.UpdateReviewStatusRequest;
import com.gtelant.commerce_service.dtos.UserRequest;
import com.gtelant.commerce_service.dtos.ReviewResponse;
import com.gtelant.commerce_service.enums.ReviewStatus;
import com.gtelant.commerce_service.mappers.ProductMapper;
import com.gtelant.commerce_service.mappers.ReviewMapper;
import com.gtelant.commerce_service.mappers.UserMapper;
import com.gtelant.commerce_service.models.Review;
import com.gtelant.commerce_service.models.User;
import com.gtelant.commerce_service.repositories.ReviewRepository;
import com.gtelant.commerce_service.services.ProductService;
import com.gtelant.commerce_service.services.ReviewService;
import com.gtelant.commerce_service.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/reviews")
@SecurityRequirement(name = "bearerAuth")
public class ReviewController {
    @Autowired
    private ReviewMapper reviewMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ReviewService reviewService;
    @Autowired
    private ReviewRepository reviewRepository;

    @Operation(summary = "Create a new review", description = "Creates a new review and returns the created review")
    @PostMapping
    public ResponseEntity<ReviewResponse> createReview(@RequestBody ReviewRequest request) {
        Review review = reviewMapper.toReview(request);
        //為避免繞過審核機制，Status統一由後端邏輯帶入。
        review.setStatus(ReviewStatus.PENDING);
        review.setUser(userService.getUserById(request.getUserId()).orElseThrow(()-> new RuntimeException("User not found!")));
        review.setProduct(productService.getProductById(request.getProductId()).orElseThrow(()-> new RuntimeException("Product not found!")));
        Review createdReview = reviewService.createReview(review);
        ReviewResponse response = reviewMapper.toReviewResponse(createdReview);
        response.setUser(userMapper.toUserResponse(createdReview.getUser()));
        response.setProduct(productMapper.toProductResponse(createdReview.getProduct()));
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "get reviews pagination")
    @GetMapping
    public ResponseEntity<ReviewResponse> getReviewPage(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size,
                                                        @RequestParam(defaultValue = "") String query) {
        //todo 分頁查詢邏輯
        return null;
    }

    @Operation(summary = "update review status")
    @PutMapping("/status")
    public ResponseEntity<List<ReviewResponse>> updateReviewStatus(@RequestBody UpdateReviewStatusRequest request) {
        List<Review> reviews = reviewService.getReviewsByIds(request.getIds());
        // id =1 ....
        // id =2 ...
        if (reviews.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<Review> updatedReview = new ArrayList<>();
        for (Review review : reviews) {
            review.setStatus(request.getReviewStatus());
            updatedReview.add(review);
        }
        //saveAll
        List<Review> createdReview = reviewService.updateReviews(updatedReview);
        //todo 批次更新後回傳型態
        List<ReviewResponse> responses = createdReview.stream().map(review -> {
            ReviewResponse response = reviewMapper.toReviewResponse(review);
            response.setUser(userMapper.toUserResponse(review.getUser()));
            response.setProduct(productMapper.toProductResponse(review.getProduct()));
            return response;
        }).toList();
        return ResponseEntity.ok(responses);
    }
}
