package com.asset.ams.dto.Response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AssetSubcategoryResponseDto {

    private Long subcategoryId;
    private String subcategoryName;
    private String description;
    private Long typeId;
    private String typeName;
}
