package com.asset.ams.mapper;

import com.asset.ams.dto.Response.TransferResponseDto;
import com.asset.ams.model.AssetTransferRequest;

public class TransferMapper {

    public static TransferResponseDto toDto(AssetTransferRequest request) {

        return TransferResponseDto.builder()
                .atrid(request.getAtrid())
                .assetName(request.getAsset().getAssetName())
                .fromLocation(request.getFromLocation().getLocationName())
                .toLocation(request.getToLocation().getLocationName())
                .status(request.getStatus().name())
                .requestedBy(request.getRequestedBy().getUserName())
                .build();
    }
}
