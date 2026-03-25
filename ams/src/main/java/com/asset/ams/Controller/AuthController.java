package com.asset.ams.Controller;

import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asset.ams.Service.AuthService;
import com.asset.ams.dto.ApiResponse;
import com.asset.ams.dto.RequestDTO.EmployeeRequestDto;
import com.asset.ams.dto.Response.EmployeeResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // @PostMapping("/register")
    // public ApiResponse<EmployeeResponseDto> register(@RequestBody EmployeeRequestDto dto) {

    //     return ApiResponse.<EmployeeResponseDto>builder()
    //             .success(true)
    //             .message("User registered successfully")
    //             .data(authService.registerUser(dto))
    //             .errorCode(0)
    //             .timestamp(LocalDateTime.now())
    //             .build();
    // }

    @PostMapping("/login")
    public ApiResponse<String> login(@RequestBody EmployeeRequestDto dto) {

        return ApiResponse.<String>builder()
                .success(true)
                .message("Login successful")
                .data(authService.login(dto))
                .errorCode(0)
                .timestamp(LocalDateTime.now())
                .build();
    }
    
}