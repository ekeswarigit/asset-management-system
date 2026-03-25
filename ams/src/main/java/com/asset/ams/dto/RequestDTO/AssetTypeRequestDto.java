package com.asset.ams.dto.RequestDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AssetTypeRequestDto {

    @NotBlank(message = "Type name is required")
    private String typeName;

    private String description;
}
