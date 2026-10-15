package com.ecommerce.ecommerceApp.controller;

import com.ecommerce.ecommerceApp.DTO.LoginRequestDto;
import com.ecommerce.ecommerceApp.DTO.UserRegistrationRequestDto;
import com.ecommerce.ecommerceApp.DTO.UserResponseDto;
import com.ecommerce.ecommerceApp.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> registerUser(@Valid @RequestBody UserRegistrationRequestDto requestDto) {
        UserResponseDto responseDto = authService.registerUser(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@Valid @RequestBody LoginRequestDto requestDto) {
        String message = authService.loginUser(requestDto);
        return ResponseEntity.ok(message);
    }
}