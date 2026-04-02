package com.asset.ams.Controller;

import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.asset.ams.Service.UserService;
import com.asset.ams.dto.ApiResponse;
import com.asset.ams.dto.RequestDTO.UserRequestDto;
import com.asset.ams.dto.Response.UserResponseDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

     @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ApiResponse<UserResponseDto> create(@Valid @RequestBody UserRequestDto dto) {

        return ApiResponse.<UserResponseDto>builder()
                .success(true)
                .message("User created")
                .data(userService.createUser(dto))
                .errorCode(200)
                .timestamp(LocalDateTime.now())
                .build();
    }

    // ADMIN + USER
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping
    public ApiResponse<Page<UserResponseDto>> getAll( 
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size) {

        Page<UserResponseDto> data = userService.getAllUser(page, size);
        return ApiResponse.<Page<UserResponseDto>>builder()
                .success(true)
                .message("User fetched")
                .data(userService.getAllUser(page, size))
                .errorCode(0)
                .timestamp(LocalDateTime.now())
                .build();
    }

    //  ADMIN + EMPLOYEE
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/{id}")
    public ApiResponse<UserResponseDto> getById(@Valid @PathVariable Long id) {

        return ApiResponse.<UserResponseDto>builder()
                .success(true)
                .message("User fetched")
                .data(userService.getUserById(id))
                .errorCode(0)
                .timestamp(LocalDateTime.now())
                .build();
    }

    // ADMIN only
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ApiResponse<UserResponseDto> update(@Valid
            @PathVariable Long id,
            @RequestBody UserRequestDto dto) {

        return ApiResponse.<UserResponseDto>builder()
                .success(true)
                .message("User updated")
                .data(userService.updateUser(id, dto))
                .errorCode(0)
                .timestamp(LocalDateTime.now())
                .build();
    }

    //  ADMIN only
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@Valid @PathVariable Long id) {

        userService.deleteUser(id);

        return ApiResponse.<String>builder()
                .success(true)
                .message("User deleted")
                .data("Deleted ID: " + id)
                .errorCode(0)
                .timestamp(LocalDateTime.now())
                .build();
    }
    
}