package com.asset.ams.Controller;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asset.ams.Service.AuthService;
import com.asset.ams.dto.ApiResponse;
import com.asset.ams.dto.RequestDTO.AuthRequestDto;
import com.asset.ams.dto.RequestDTO.EmployeeRequestDto;
import com.asset.ams.dto.RequestDTO.RegisterRequestDto;
import com.asset.ams.dto.Response.EmployeeResponseDto;
import com.asset.ams.dto.Response.LoginResponseDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // @PostMapping("/register")
    // public ApiResponse<EmployeeResponseDto> register(@Valid @RequestBody RegisterRequestDto dto) {

    //     return ApiResponse.<EmployeeResponseDto>builder()
    //             .success(true)
    //             .message("User registered successfully")
    //             .data(authService.registerEmployee(dto))
    //             .errorCode(0)
    //             .timestamp(LocalDateTime.now())
    //             .build();
    // }

@PostMapping("/login")
public ResponseEntity<ApiResponse<LoginResponseDto>> login(
        @RequestBody AuthRequestDto request) {

    LoginResponseDto response = authService.login(request);

    return ResponseEntity.ok(
        new ApiResponse<>(true, "Login successful", response, 200, LocalDateTime.now())
    );
}
    
}