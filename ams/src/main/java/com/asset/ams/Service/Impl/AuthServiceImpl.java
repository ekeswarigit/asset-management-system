package com.asset.ams.Service.Impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.asset.ams.Repository.EmployeeRepository;
import com.asset.ams.Service.AuthService;
import com.asset.ams.config.JwtUtil;
import com.asset.ams.dto.RequestDTO.AuthRequestDto;
import com.asset.ams.dto.RequestDTO.EmployeeRequestDto;
import com.asset.ams.dto.Response.EmployeeResponseDto;
import com.asset.ams.mapper.EmployeeMapper;
import com.asset.ams.model.Employee;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
     public EmployeeResponseDto registerUser(EmployeeRequestDto dto) {

        Employee user = EmployeeMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Employee savedUser = employeeRepository.save(user);
        return EmployeeMapper.toDto(savedUser);
    }
    @Override
    public String login(AuthRequestDto dto) throws RuntimeException {
        
        Employee emp = employeeRepository.findByEmpName(dto.getEmpName())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        if (!passwordEncoder.matches(dto.getPassword(), emp.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        return jwtUtil.generateToken(emp.getEmpName());
    }
}

