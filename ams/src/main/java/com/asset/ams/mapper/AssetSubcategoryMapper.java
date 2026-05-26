package com.asset.ams.mapper;

import com.asset.ams.dto.RequestDTO.AssetSubcategoryRequestDto;
import com.asset.ams.dto.Response.AssetSubcategoryResponseDto;
import com.asset.ams.model.AssetSubcategory;
import com.asset.ams.model.AssetType;

public class AssetSubcategoryMapper {

    public static AssetSubcategory toEntity(AssetSubcategoryRequestDto dto, AssetType type) {
        return AssetSubcategory.builder()
                .subcategoryName(dto.getSubcategoryName())
                .description(dto.getDescription())
                .assetType(type)
                .build();
    }

    public static AssetSubcategoryResponseDto toDto(AssetSubcategory subcategory) {
        return AssetSubcategoryResponseDto.builder()
                .subcategoryId(subcategory.getSubcategoryId())
                .subcategoryName(subcategory.getSubcategoryName())
                .description(subcategory.getDescription())
                .typeId(subcategory.getAssetType() != null ? subcategory.getAssetType().getTypeId() : null)
                .typeName(subcategory.getAssetType() != null ? subcategory.getAssetType().getTypeName() : null)
                .build();
    }
}
