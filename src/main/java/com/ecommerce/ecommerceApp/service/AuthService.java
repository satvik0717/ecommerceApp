package com.ecommerce.ecommerceApp.service;

import com.ecommerce.ecommerceApp.DTO.LoginRequestDto;
import com.ecommerce.ecommerceApp.DTO.UserRegistrationRequestDto;
import com.ecommerce.ecommerceApp.DTO.UserResponseDto;
import com.ecommerce.ecommerceApp.Entity.Role;
import com.ecommerce.ecommerceApp.Entity.User;
import com.ecommerce.ecommerceApp.exception.BadRequestException;
import com.ecommerce.ecommerceApp.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public UserResponseDto registerUser(UserRegistrationRequestDto requestDto) {
        if (userRepo.existsByUsername(requestDto.getUsername())) {
            throw new BadRequestException("Username is already taken: " + requestDto.getUsername());
        }

        Role assignedRole = requestDto.getRole() != null ? requestDto.getRole() : Role.ROLE_USER;

        User user = User.builder()
                .username(requestDto.getUsername())
                .password(passwordEncoder.encode(requestDto.getPassword())) // BCrypt hashing
                .role(assignedRole)
                .build();

        User savedUser = userRepo.save(user);

        return UserResponseDto.builder()
                .id(savedUser.getId())
                .username(savedUser.getUsername())
                .role(savedUser.getRole())
                .build();
    }

    public String loginUser(LoginRequestDto requestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDto.getUsername(),
                        requestDto.getPassword()
                )
        );
        return "User logged in successfully: " + authentication.getName();
    }
}