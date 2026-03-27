package com.asset.ams.Service.Impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.asset.ams.Repository.EmployeeRepository;
import com.asset.ams.Repository.RoleRepository;
import com.asset.ams.Service.AuthService;
import com.asset.ams.config.JwtUtil;
import com.asset.ams.dto.RequestDTO.AuthRequestDto;
import com.asset.ams.dto.RequestDTO.RegisterRequestDto;
import com.asset.ams.dto.Response.EmployeeResponseDto;
import com.asset.ams.dto.Response.LoginResponseDto;
import com.asset.ams.mapper.EmployeeMapper;
import com.asset.ams.model.Employee;
import com.asset.ams.model.Role;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RoleRepository roleRepository;

    // @Override
    //  public EmployeeResponseDto registerEmployee(RegisterRequestDto dto) {

    //     Role role = roleRepository.findByRoleName("EMPLOYEE")
    //         .orElseThrow(() -> new RuntimeException("Role not found"));

    //     Employee user = EmployeeMapper.fromRegisterDto(dto, role);
    //     user.setPassword(passwordEncoder.encode(user.getPassword()));
    //     Employee savedUser = employeeRepository.save(user);
    //     return EmployeeMapper.toDto(savedUser);
    // }
@Override
public LoginResponseDto login(AuthRequestDto dto) {

    Employee emp = employeeRepository.findByEmail(dto.getEmail())
            .orElseThrow(() -> new RuntimeException("Invalid email or password"));

    // Check soft delete
    // if (emp.isDeleted()) {
    //     throw new RuntimeException("User account is inactive");
    // }

    // Validate password
    if (!passwordEncoder.matches(dto.getPassword(), emp.getPassword())) {
        throw new RuntimeException("Invalid email or password");
    }

    // Generate token
    String token = jwtUtil.generateToken(emp.getEmail(), emp.getRole().getRoleName());

    return new LoginResponseDto(
            emp.getEmpName(),
            emp.getEmail(),
            emp.getRole().getRoleName(), token
    );
}
}

