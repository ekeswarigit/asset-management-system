package com.asset.ams.mapper;

import com.asset.ams.dto.RequestDTO.EmployeeRequestDto;
import com.asset.ams.dto.Response.EmployeeResponseDto;
import com.asset.ams.model.Employee;

public class EmployeeMapper {

       // DTO → Entity
    public static Employee toEntity(EmployeeRequestDto dto) {
        // Employee user = new Employee();
        // user.setEmpName(dto.getEmpName());
        // user.setPassword(dto.getPassword());
        // return user;
         return Employee.builder()
                .empName(dto.getEmpName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .role(dto.getRole())
                .build();
    }

    // Entity → DTO
    public static EmployeeResponseDto toDto(Employee emp) {
        return EmployeeResponseDto.builder()
                .empId(emp.getEmpId())
                .empName(emp.getEmpName())
                .email(emp.getEmail())
                .role(emp.getRole())
                .build();
    }
}