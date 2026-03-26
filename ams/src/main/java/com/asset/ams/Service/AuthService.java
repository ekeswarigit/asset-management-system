package com.asset.ams.Service;

import com.asset.ams.dto.RequestDTO.AuthRequestDto;
import com.asset.ams.dto.RequestDTO.EmployeeRequestDto;
import com.asset.ams.dto.Response.EmployeeResponseDto;

public interface AuthService {

    EmployeeResponseDto registerEmployee(EmployeeRequestDto dto);
    
    String login(AuthRequestDto dto);
}
