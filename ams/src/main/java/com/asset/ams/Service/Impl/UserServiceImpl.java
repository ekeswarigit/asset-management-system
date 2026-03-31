package com.asset.ams.Service.Impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.asset.ams.Repository.UserRepository;
import com.asset.ams.Repository.RoleRepository;
import com.asset.ams.Service.UserService;
import com.asset.ams.dto.RequestDTO.UserRequestDto;
import com.asset.ams.dto.Response.UserResponseDto;
import com.asset.ams.mapper.UserMapper;
import com.asset.ams.model.User;
import com.asset.ams.model.Role;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final SoftDeleteServiceImpl softDeleteServiceimpl;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto createUser(UserRequestDto dto) {

        Role role = roleRepository.findById(dto.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        User emp = UserMapper.toEntity(dto, role);

        emp.setPassword(passwordEncoder.encode(emp.getPassword()));

        return UserMapper.toDto(userRepository.save(emp));
    }

    @Override
    public Page<UserResponseDto> getAllUser(int page, int size) {

         Pageable pageable = PageRequest.of(page, size);

         Page<User> user = userRepository.findAll(pageable);
         return user.map(UserMapper::toDto);
        // return employeeRepository.findAll()
        //         .stream()
        //         .filter(emp -> !emp.isDeleted())
        //         .map(EmployeeMapper::toDto)
        //         .toList();
    }

    @Override
    public UserResponseDto getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return UserMapper.toDto(user);
    }

    @Override
    public UserResponseDto updateUser(Long id, UserRequestDto dto) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Role role = roleRepository.findById(dto.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        user.setUserName(dto.getUserName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRole(role);

        return UserMapper.toDto(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        softDeleteServiceimpl.softDelete(user, "admin");

        userRepository.save(user);
    }
     //  RESTORE
    @Override
    public void restoreUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        softDeleteServiceimpl.restore(user);

        userRepository.save(user);
    }
}