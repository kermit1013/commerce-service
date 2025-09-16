package com.gtelant.commerce_service.controllers;

import com.gtelant.commerce_service.dtos.AuthResponse;
import com.gtelant.commerce_service.dtos.LoginRequest;
import com.gtelant.commerce_service.dtos.UserRequest;
import com.gtelant.commerce_service.mappers.UserMapper;
import com.gtelant.commerce_service.models.User;
import com.gtelant.commerce_service.services.JwtAuthService;
import com.gtelant.commerce_service.services.UserService;
import jdk.jfr.Registered;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jwt")
public class JwtAutController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;

    @Autowired
    private JwtAuthService jwtAuthService;
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody UserRequest userRequest) {
        User user = userMapper.toUser(userRequest);
        String token = jwtAuthService.register(user);
        return ResponseEntity.ok(new AuthResponse(token));
    }


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        String token = jwtAuthService.login(request);
        return ResponseEntity.ok(new AuthResponse(token));

    }
}
