package com.asset.ams.mapper;

import com.asset.ams.dto.RequestDTO.UserRequestDto;
import com.asset.ams.dto.RequestDTO.RegisterRequestDto;
import com.asset.ams.dto.Response.UserResponseDto;
import com.asset.ams.model.User;
import com.asset.ams.model.Role;

public class UserMapper {

    public static User toEntity(UserRequestDto dto, Role role) {
        return User.builder()
                .userName(dto.getUserName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .role(role)
                .build();
    }

     public static User fromRegisterDto(RegisterRequestDto dto, Role role) {
        return User.builder()
                .userName(dto.getUserName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .role(role)
                .build();
    }

    public static UserResponseDto toDto(User emp) {
        return UserResponseDto.builder()
                .userId(emp.getUserId())
                .userName(emp.getUserName())
                .email(emp.getEmail())
                .roleName(emp.getRole().getRoleName())
                .build();
    }
}