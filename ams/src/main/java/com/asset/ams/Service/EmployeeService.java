package com.asset.ams.Service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.asset.ams.dto.RequestDTO.EmployeeRequestDto;
import com.asset.ams.dto.Response.EmployeeResponseDto;

public interface EmployeeService {

    EmployeeResponseDto createEmployee(EmployeeRequestDto dto);

    Page<EmployeeResponseDto> getAllEmployees(int page, int size);

    EmployeeResponseDto getEmployeeById(Long id);

    EmployeeResponseDto updateEmployee(Long id, EmployeeRequestDto dto);

    void deleteEmployee(Long id);

    void restoreEmployee(Long id);

}
