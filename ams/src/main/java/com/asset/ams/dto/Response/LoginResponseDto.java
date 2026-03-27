package com.asset.ams.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
//@AllArgsConstructor

public class LoginResponseDto {

    private String name;
    private String email;
    private String role;
    private String token;

    public LoginResponseDto(String name, String email, String role, String token) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.token = token;
    }
}
