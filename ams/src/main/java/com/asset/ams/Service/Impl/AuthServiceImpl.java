package com.asset.ams.Service.Impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.asset.ams.Repository.RoleRepository;
import com.asset.ams.Repository.UserRepository;
import com.asset.ams.Service.AuthService;
import com.asset.ams.config.JwtUtil;
import com.asset.ams.dto.RequestDTO.AuthRequestDto;
import com.asset.ams.dto.RequestDTO.RegisterRequestDto;
import com.asset.ams.dto.Response.LoginResponseDto;
import com.asset.ams.dto.Response.UserResponseDto;
import com.asset.ams.mapper.UserMapper;
import com.asset.ams.model.Role;
import com.asset.ams.model.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder encoder;

    @Override
     public UserResponseDto registerUser(RegisterRequestDto dto) {
                                  //(  "USER")
        Role role = roleRepository.findByRoleName(dto.getRole().toUpperCase())
            .orElseThrow(() -> new RuntimeException("Role not found"));

        User user = UserMapper.fromRegisterDto(dto, role);
        user.setPassword(encoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        return UserMapper.toDto(savedUser);
    }
@Override
public LoginResponseDto login(AuthRequestDto dto) {

    User user = userRepository.findByEmail(dto.getEmail())
            .orElseThrow(() -> new RuntimeException("Invalid email or password"));

    // Check soft delete
     if (user.isDeleted()) {
         throw new RuntimeException("User account is inactive");
     }

    // Validate password
    if (!encoder.matches(dto.getPassword(), user.getPassword())) {
        throw new RuntimeException("Invalid password");
    }

    // Generate token
    String token = jwtUtil.generateToken(user.getEmail(), user.getRole().getRoleName());

    return new LoginResponseDto(
            user.getUserName(),
            user.getEmail(),
            user.getRole().getRoleName(), token
    );
 }
}

