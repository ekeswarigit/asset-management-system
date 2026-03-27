package com.asset.ams.Service;

import com.asset.ams.dto.RequestDTO.AuthRequestDto;
import com.asset.ams.dto.RequestDTO.RegisterRequestDto;
import com.asset.ams.dto.Response.EmployeeResponseDto;
import com.asset.ams.dto.Response.LoginResponseDto;

public interface AuthService {

   // EmployeeResponseDto registerEmployee(RegisterRequestDto dto);
    
    LoginResponseDto login(AuthRequestDto dto);
}
