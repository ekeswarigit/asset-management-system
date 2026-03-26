package com.asset.ams.Service;

import java.util.List;

import com.asset.ams.dto.RequestDTO.EmployeeRequestDto;
import com.asset.ams.dto.Response.EmployeeResponseDto;

public interface EmployeeService {

    EmployeeResponseDto createEmployee(EmployeeRequestDto dto);

    List<EmployeeResponseDto> getAllEmployees();

    EmployeeResponseDto getEmployeeById(Long id);

    EmployeeResponseDto updateEmployee(Long id, EmployeeRequestDto dto);

    void deleteEmployee(Long id);

}
