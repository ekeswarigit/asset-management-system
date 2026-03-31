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
import com.asset.ams.dto.RequestDTO.RegisterRequestDto;
import com.asset.ams.dto.Response.UserResponseDto;
// import com.asset.ams.dto.RequestDTO.EmployeeRequestDto;
// import com.asset.ams.dto.RequestDTO.RegisterRequestDto;
// import com.asset.ams.dto.Response.EmployeeResponseDto;
import com.asset.ams.dto.Response.LoginResponseDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ApiResponse<UserResponseDto> register(@Valid @RequestBody RegisterRequestDto dto) {

        return ApiResponse.<UserResponseDto>builder()
                .success(true)
                .message("User registered successfully")
                .data(authService.registerUser(dto))
                .errorCode(0)
                .timestamp(LocalDateTime.now())
                .build();
    }
@PostMapping("/login")
public ResponseEntity<ApiResponse<LoginResponseDto>> login(@Valid @RequestBody AuthRequestDto request) {

    LoginResponseDto response = authService.login(request);

    return ResponseEntity.ok(
       ApiResponse.<LoginResponseDto>builder()
                    .success(true)
                    .message("Login successful")
                    .data(response)
                    .errorCode(0) // or 200 if you're using HTTP-like codes
                    .timestamp(LocalDateTime.now())
                    .build()
    );

}
    
}