package com.asset.ams.Service;

import java.util.List;

import com.asset.ams.dto.RequestDTO.RoleRequestDto;
import com.asset.ams.dto.Response.RoleResponseDto;

public interface RoleService {

    RoleResponseDto createRole(RoleRequestDto role);
    List<RoleResponseDto> getAllRoles();
    RoleResponseDto getRoleByName(String roleName);
}
