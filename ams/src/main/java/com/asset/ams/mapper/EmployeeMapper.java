package com.asset.ams.mapper;

import com.asset.ams.dto.RequestDTO.EmployeeRequestDto;
import com.asset.ams.dto.RequestDTO.RegisterRequestDto;
import com.asset.ams.dto.Response.EmployeeResponseDto;
import com.asset.ams.model.Employee;
import com.asset.ams.model.Role;

public class EmployeeMapper {

    public static Employee toEntity(EmployeeRequestDto dto, Role role) {
        return Employee.builder()
                .empName(dto.getEmpName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .role(role)
                .build();
    }

     public static Employee fromRegisterDto(RegisterRequestDto dto, Role role) {
        return Employee.builder()
                .empName(dto.getEmpName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .role(role)
                .build();
    }

    public static EmployeeResponseDto toDto(Employee emp) {
        return EmployeeResponseDto.builder()
                .empId(emp.getEmpId())
                .empName(emp.getEmpName())
                .email(emp.getEmail())
                .roleName(emp.getRole().getRoleName())
                .build();
    }
}