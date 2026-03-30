package com.asset.ams.mapper;

import com.asset.ams.dto.RequestDTO.AssetRequestDto;
import com.asset.ams.dto.Response.AssetResponseDto;
import com.asset.ams.model.Asset;
import com.asset.ams.model.AssetType;
import com.asset.ams.model.Location;

public class AssetMapper {

    // 📥 DTO → Entity
    public static Asset toEntity(AssetRequestDto dto,
                                 AssetType type,
                                 Location location) {

        Asset asset = new Asset();

        asset.setAssetName(dto.getAssetName());
        asset.setSerialNumber(dto.getSerialNumber());
        asset.setBrand(dto.getBrand());
        asset.setModel(dto.getModel());
        asset.setPurchaseDate(dto.getPurchaseDate());
        asset.setWarrantyExpiry(dto.getWarrantyExpiry());
        asset.setCost(dto.getCost());
        asset.setStatus(dto.getStatus());
        asset.setAssetCondition(dto.getAssetCondition());
        asset.setNotes(dto.getNotes());

        // Relationships
        asset.setAssetType(type);
        asset.setLocation(location);

        return asset;
    }

    // 📤 Entity → DTO
    public static AssetResponseDto toDto(Asset asset) {

        return AssetResponseDto.builder()
                .assetId(asset.getAssetId())
                .assetName(asset.getAssetName())
                .serialNumber(asset.getSerialNumber())
                .brand(asset.getBrand())
                .model(asset.getModel())
                .purchaseDate(asset.getPurchaseDate())
                .warrantyExpiry(asset.getWarrantyExpiry())
                .cost(asset.getCost())
                .status(asset.getStatus())
                .assetCondition(asset.getAssetCondition())
                .notes(asset.getNotes())

                // Show names instead of full objects
                .assetTypeName(asset.getAssetType().getTypeName())
                .locationName(asset.getLocation().getLocationName())

                .build();
    }
}  
    