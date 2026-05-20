package com.asset.ams.mapper;

import com.asset.ams.dto.Response.TransferResponseDto;
import com.asset.ams.model.AssetTransferRequest;

public class TransferMapper {

    public static TransferResponseDto toDto(AssetTransferRequest request) {

        return TransferResponseDto.builder()
                .atrid(request.getAtrid())
                .assetId(request.getAsset() != null ? request.getAsset().getAssetId() : null)
                .assetName(request.getAsset().getAssetName())
                .fromLocation(request.getFromLocation() != null ? request.getFromLocation().getLocationName() : "None")
                .toLocation(request.getToLocation() != null ? request.getToLocation().getLocationName() : "Unknown Location")
                .status(request.getStatus().name())
                .requestedBy(request.getRequestedBy().getUserName())
                .build();
    }
}
