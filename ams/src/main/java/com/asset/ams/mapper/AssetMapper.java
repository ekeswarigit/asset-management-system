package com.asset.ams.mapper;

import com.asset.ams.dto.RequestDTO.AssetRequestDto;
import com.asset.ams.dto.Response.AssetResponseDto;
import com.asset.ams.dto.Response.AssignResponseDto;
import com.asset.ams.model.Asset;
import com.asset.ams.model.AssetType;
import com.asset.ams.model.Location;
import com.asset.ams.payload.AssetCondition;
import com.asset.ams.payload.AssetStatus;

public class AssetMapper {

    // 📥 DTO → Entity
    public static Asset toEntity(AssetRequestDto dto, AssetType type, Location location) {

        Asset asset = new Asset();

        asset.setAssetName(dto.getAssetName());
        asset.setSerialNumber(dto.getSerialNumber());
        asset.setBrand(dto.getBrand());
        asset.setModel(dto.getModel());
        asset.setPurchaseDate(dto.getPurchaseDate());
        asset.setWarrantyExpiry(dto.getWarrantyExpiry());
        asset.setCost(dto.getCost());
        asset.setStatus(AssetStatus.valueOf(dto.getStatus().toUpperCase()));
        asset.setAssetCondition(AssetCondition.valueOf(dto.getAssetCondition().toUpperCase()));
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
                .status(asset.getStatus().name())
                .assetCondition(asset.getAssetCondition().name())
                .notes(asset.getNotes())

                // Show names instead of full objects
                .assetTypeName(asset.getAssetType().getTypeName())
                .locationName(asset.getLocation().getLocationName())
                .assignedUserName(asset.getAssignedTo() != null ? asset.getAssignedTo().getUserName() : null)

                .build();
    }

        //  Entity → Assign Response DTO
        public static AssignResponseDto toAssignDto(Asset asset) {

            return AssignResponseDto.builder()
                    .assetId(asset.getAssetId())
                    .assetName(asset.getAssetName())

                    .userId(asset.getAssignedTo() != null ? asset.getAssignedTo().getUserId() : null)
                    .userName(asset.getAssignedTo() != null ? asset.getAssignedTo().getUserName() : null)

                    .status(asset.getStatus() != null ? asset.getStatus().getValue() : null)
                    .build();
        }
}  
    