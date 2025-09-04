package com.gtelant.commerce_service.dtos;

import com.gtelant.commerce_service.models.User;
import com.gtelant.commerce_service.models.UserSegment;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserResponse {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birthday;
    private String address;
    private String city;
    private String zipcode;
    private String role;
    private boolean hasNewsletter;
    private LocalDateTime lastSeenAt;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
    private List<UserSegmentResponse> userSegments;
}
