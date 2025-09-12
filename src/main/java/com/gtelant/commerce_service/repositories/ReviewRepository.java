package com.gtelant.commerce_service.repositories;

import com.gtelant.commerce_service.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
}
