package com.asset.ams.dto.RequestDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmployeeRequestDto {

    @NotBlank(message = "Type name is required")
    private String empName;
    private String email;
    @NotBlank(message = "Password is required")
    private String password;
    private Long roleId;
}
