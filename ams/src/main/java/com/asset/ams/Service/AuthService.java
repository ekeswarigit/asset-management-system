package com.asset.ams.Service;

import com.asset.ams.dto.RequestDTO.EmployeeRequestDto;
import com.asset.ams.dto.Response.EmployeeResponseDto;

public interface AuthService {

   // EmployeeResponseDto registerUser(EmployeeRequestDto dto);
    
    String login(EmployeeRequestDto Dto);
}
