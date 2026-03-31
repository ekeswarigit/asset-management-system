package com.asset.ams.Service;

import org.springframework.data.domain.Page;

import com.asset.ams.dto.RequestDTO.UserRequestDto;
import com.asset.ams.dto.Response.UserResponseDto;

public interface UserService {

    UserResponseDto createUser(UserRequestDto dto);

    Page<UserResponseDto> getAllUser(int page, int size);

    UserResponseDto getUserById(Long id);

    UserResponseDto updateUser(Long id, UserRequestDto dto);

    void deleteUser(Long id);

    void restoreUser(Long id);

}
