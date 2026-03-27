package com.asset.ams.Controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
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

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

     @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ApiResponse<EmployeeResponseDto> create(@Valid @RequestBody EmployeeRequestDto dto) {

        return ApiResponse.<EmployeeResponseDto>builder()
                .success(true)
                .message("Employee created")
                .data(employeeService.createEmployee(dto))
                .errorCode(200)
                .timestamp(LocalDateTime.now())
                .build();
    }

    // ✅ ADMIN + EMPLOYEE
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @GetMapping
    public ApiResponse<List<EmployeeResponseDto>> getAll() {

        return ApiResponse.<List<EmployeeResponseDto>>builder()
                .success(true)
                .message("Employees fetched")
                .data(employeeService.getAllEmployees())
                .errorCode(0)
                .timestamp(LocalDateTime.now())
                .build();
    }

    // ✅ ADMIN + EMPLOYEE
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @GetMapping("/{id}")
    public ApiResponse<EmployeeResponseDto> getById(@Valid @PathVariable Long id) {

        return ApiResponse.<EmployeeResponseDto>builder()
                .success(true)
                .message("Employee fetched")
                .data(employeeService.getEmployeeById(id))
                .errorCode(0)
                .timestamp(LocalDateTime.now())
                .build();
    }

    // ✅ ADMIN only
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ApiResponse<EmployeeResponseDto> update(
            @PathVariable Long id,
            @RequestBody EmployeeRequestDto dto) {

        return ApiResponse.<EmployeeResponseDto>builder()
                .success(true)
                .message("Employee updated")
                .data(employeeService.updateEmployee(id, dto))
                .errorCode(0)
                .timestamp(LocalDateTime.now())
                .build();
    }

    // ✅ ADMIN only
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@Valid @PathVariable Long id) {

        employeeService.deleteEmployee(id);

        return ApiResponse.<String>builder()
                .success(true)
                .message("Employee deleted")
                .data("Deleted ID: " + id)
                .errorCode(0)
                .timestamp(LocalDateTime.now())
                .build();
    }
    
}