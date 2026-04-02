package com.asset.ams.Service;

import com.asset.ams.dto.RequestDTO.AuthRequestDto;
import com.asset.ams.dto.RequestDTO.RegisterRequestDto;
import com.asset.ams.dto.Response.LoginResponseDto;
import com.asset.ams.dto.Response.UserResponseDto;

public interface AuthService {

   // UserResponseDto registerUser(RegisterRequestDto dto);
    
    LoginResponseDto login(AuthRequestDto dto);
}
