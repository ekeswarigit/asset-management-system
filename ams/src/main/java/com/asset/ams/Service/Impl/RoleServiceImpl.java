package com.asset.ams.Service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.asset.ams.Repository.RoleRepository;
import com.asset.ams.Service.RoleService;
import com.asset.ams.dto.RequestDTO.RoleRequestDto;
import com.asset.ams.dto.Response.RoleResponseDto;
import com.asset.ams.model.Role;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

   private final RoleRepository roleRepository;

    @Override
    public RoleResponseDto createRole(RoleRequestDto dto) {
        Role role = Role.builder()
                .roleName(dto.getRoleName().toUpperCase())
                .build();

        Role savedRole = roleRepository.save(role);

        return mapToDTO(savedRole);  
    }

    @Override
    public List<RoleResponseDto> getAllRoles() {
        return roleRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public RoleResponseDto getRoleByName(String roleName) {
        Role role = roleRepository.findByRoleName(roleName.toUpperCase())
                .orElseThrow(() -> new RuntimeException("Role not found with name: " + roleName));

        return mapToDTO(role);
    } 

     // ✅ Entity → DTO Mapper
    private RoleResponseDto mapToDTO(Role role) {
        return RoleResponseDto.builder()
                .roleId(role.getRoleId())
                .roleName(role.getRoleName())
                .build();
    }           
}
