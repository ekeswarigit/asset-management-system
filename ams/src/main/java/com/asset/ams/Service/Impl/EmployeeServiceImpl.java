package com.asset.ams.Service.Impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.asset.ams.Repository.EmployeeRepository;
import com.asset.ams.Repository.RoleRepository;
import com.asset.ams.Service.EmployeeService;
import com.asset.ams.dto.RequestDTO.EmployeeRequestDto;
import com.asset.ams.dto.Response.EmployeeResponseDto;
import com.asset.ams.mapper.EmployeeMapper;
import com.asset.ams.model.Employee;
import com.asset.ams.model.Role;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    
    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final SoftDeleteServiceImpl softDeleteServiceimpl;
    private final PasswordEncoder passwordEncoder;

    @Override
    public EmployeeResponseDto createEmployee(EmployeeRequestDto dto) {

        Role role = roleRepository.findById(dto.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        Employee emp = EmployeeMapper.toEntity(dto, role);

        emp.setPassword(passwordEncoder.encode(emp.getPassword()));

        return EmployeeMapper.toDto(employeeRepository.save(emp));
    }

    @Override
    public Page<EmployeeResponseDto> getAllEmployees(int page, int size) {

         Pageable pageable = PageRequest.of(page, size);

         Page<Employee> employees = employeeRepository.findAll(pageable);
         return employees.map(EmployeeMapper::toDto);
        // return employeeRepository.findAll()
        //         .stream()
        //         .filter(emp -> !emp.isDeleted())
        //         .map(EmployeeMapper::toDto)
        //         .toList();
    }

    @Override
    public EmployeeResponseDto getEmployeeById(Long id) {

        Employee emp = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        return EmployeeMapper.toDto(emp);
    }

    @Override
    public EmployeeResponseDto updateEmployee(Long id, EmployeeRequestDto dto) {

        Employee emp = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        Role role = roleRepository.findById(dto.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        emp.setEmpName(dto.getEmpName());
        emp.setEmail(dto.getEmail());
        emp.setPassword(dto.getPassword());
        emp.setRole(role);

        return EmployeeMapper.toDto(employeeRepository.save(emp));
    }

    @Override
    public void deleteEmployee(Long id) {

        Employee emp = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        softDeleteServiceimpl.softDelete(emp, "admin");

        employeeRepository.save(emp);
    }
     //  RESTORE
    @Override
    public void restoreEmployee(Long id) {

        Employee emp = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        softDeleteServiceimpl.restore(emp);

        employeeRepository.save(emp);
    }
}