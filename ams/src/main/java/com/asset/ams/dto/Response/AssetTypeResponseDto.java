package com.asset.ams.dto.Response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AssetTypeResponseDto {

    private Long typeId;
    private String typeName;
    private String description;
}
