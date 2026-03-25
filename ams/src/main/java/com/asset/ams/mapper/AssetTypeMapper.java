package com.asset.ams.mapper;


import com.asset.ams.dto.RequestDTO.AssetTypeRequestDto;
import com.asset.ams.dto.Response.AssetTypeResponseDto;
import com.asset.ams.model.AssetType;

public class AssetTypeMapper {

    // DTO → Entity
    public static AssetType toEntity(AssetTypeRequestDto dto) {
        return AssetType.builder()
                .typeName(dto.getTypeName())
                .description(dto.getDescription())
                .build();
    }

    // Entity → DTO
    public static AssetTypeResponseDto toDto(AssetType type) {
        return AssetTypeResponseDto.builder()
                .typeId(type.getTypeId())
                .typeName(type.getTypeName())
                .description(type.getDescription())
                .build();
    }
}