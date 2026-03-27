package com.asset.ams.dto.RequestDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequestDto {

    @NotBlank(message = "Type name is required")
    private String empName;
    private String email;
    @NotBlank(message = "Password is required")
    private String password;
}
