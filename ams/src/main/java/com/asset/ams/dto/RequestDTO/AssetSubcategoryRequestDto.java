package com.asset.ams.dto.RequestDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AssetSubcategoryRequestDto {

    @NotBlank(message = "Subcategory name is required")
    private String subcategoryName;

    private String description;

    @NotNull(message = "Asset Type ID is required")
    private Long typeId;
}
