package com.asset.ams.dto.Response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeResponseDto {

    private Long empId;
    private String empName;
    private String email;
    private String roleName;
}
