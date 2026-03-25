package com.asset.ams.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class RoleResponseDto {

    private Long roleId;
    private String roleName;
}
