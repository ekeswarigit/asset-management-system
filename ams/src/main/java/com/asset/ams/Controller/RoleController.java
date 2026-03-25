package com.asset.ams.Controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.asset.ams.Service.RoleService;
import com.asset.ams.dto.ApiResponse;
import com.asset.ams.dto.RequestDTO.RoleRequestDto;
import com.asset.ams.dto.Response.RoleResponseDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;
   
    @PostMapping
    public ApiResponse<RoleResponseDto> createRole(@Valid @RequestBody RoleRequestDto dto) {

        return  ApiResponse.<RoleResponseDto>builder()
                .success(true)
                .message("Role created successfully")
                .data(roleService.createRole(dto))
                .errorCode(0)
                .timestamp(LocalDateTime.now())
                .build();
    }

    @GetMapping
    public ApiResponse<List<RoleResponseDto>> getAllRoles() {

        return  ApiResponse.<List<RoleResponseDto>>builder()
                .success(true)
                .message("role fetched successfully")
                .data(roleService.getAllRoles())
                .errorCode(0)
                .timestamp(LocalDateTime.now())
                .build();
    }

    @GetMapping("/name/{roleName}")
    public ApiResponse<RoleResponseDto> getRoleByName( @PathVariable String roleName) {
        return  ApiResponse.<RoleResponseDto>builder()
                .success(true)
                .message("Role created successfully")
                .data(roleService.getRoleByName(roleName))
                .errorCode(0)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
