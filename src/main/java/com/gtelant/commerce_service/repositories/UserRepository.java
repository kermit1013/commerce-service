package com.gtelant.commerce_service.repositories;

import com.gtelant.commerce_service.models.User;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    Page<User> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName, Pageable pageable);
    List<User> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);

    Page<User> findByHasNewsletter(boolean hasNewsletter, Pageable pageable);
    List<User> findByHasNewsletter(boolean hasNewsletter);

    Page<User> findByUserSegmentsSegmentId(int segmentId, Pageable pageable);
    List<User> findByUserSegmentsSegmentId(int segmentId);

}
