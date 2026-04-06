package com.asset.ams.dto.Response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AssignResponseDto {

     private Long assetId;
    private String assetName;
    private Long userId;
    private String userName;
    private String status;
}
