package com.asset.ams.Controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asset.ams.Service.EmployeeService;
import com.asset.ams.dto.ApiResponse;
import com.asset.ams.dto.RequestDTO.EmployeeRequestDto;
import com.asset.ams.dto.Response.EmployeeResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    // @PostMapping
    // public ApiResponse<EmployeeResponseDto> addEmployee(@RequestBody EmployeeRequestDto dto) {

    //     EmployeeResponseDto saved = employeeService.createEmployee(dto);

    //     return ApiResponse.<EmployeeResponseDto>builder()
    //             .success(true)
    //             .message("Employee created successfully")
    //             .data(saved)
    //             .errorCode(0)
    //             .timestamp(LocalDateTime.now())
    //             .build();
    // }

    // @PutMapping("/{id}")
    // public ApiResponse<EmployeeResponseDto> updateEmployee(
    //         @PathVariable Long id,
    //         @RequestBody EmployeeRequestDto dto) {

    //     EmployeeResponseDto updated = employeeService.updateEmployee(id, dto);

    //     return ApiResponse.<EmployeeResponseDto>builder()
    //             .success(true)
    //             .message("Employee updated successfully")
    //             .data(updated)
    //             .errorCode(0)
    //             .timestamp(LocalDateTime.now())
    //             .build();
    // }

    //  Soft Delete Employee
    // @DeleteMapping("/{id}")
    // public ApiResponse<String> deleteEmployee(@PathVariable Long id) {

    //     employeeService.deleteEmployee(id);

    //     return ApiResponse.<String>builder()
    //             .success(true)
    //             .message("Employee deleted successfully")
    //             .data("Deleted ID: " + id)
    //             .errorCode(0)
    //             .timestamp(LocalDateTime.now())
    //             .build();
    // } 

    // 📄 Get All Employees
    // @GetMapping
    // public ApiResponse<List<EmployeeResponseDto>> getAllEmployees() {

    //     List<EmployeeResponseDto> list = employeeService.getAllEmployees();

    //     return ApiResponse.<List<EmployeeResponseDto>>builder()
    //             .success(true)
    //             .message("Employees fetched successfully")
    //             .data(list)
    //             .errorCode(0)
    //             .timestamp(LocalDateTime.now())
    //             .build();
    // }

    // 🔍 Get Employee By ID
    // @GetMapping("/{id}")
    // public ApiResponse<EmployeeResponseDto> getEmployeeById(@PathVariable Long id) {

    //     EmployeeResponseDto emp = employeeService.getEmployeeById(id);

    //     return ApiResponse.<EmployeeResponseDto>builder()
    //             .success(true)
    //             .message("Employee fetched successfully")
    //             .data(emp)
    //             .errorCode(0)
    //             .timestamp(LocalDateTime.now())
    //             .build();
    // }
    
}