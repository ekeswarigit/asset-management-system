package com.asset.ams.dto.RequestDTO;

import com.asset.ams.model.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmployeeRequestDto {

    @NotBlank(message = "Type name is required")
    private String empName;
    @NotBlank(message = "Password is required")
    private String password;
    private String email;
    private Role role;
}
