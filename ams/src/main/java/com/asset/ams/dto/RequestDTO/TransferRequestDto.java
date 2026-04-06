package com.asset.ams.dto.RequestDTO;

import lombok.Data;

@Data
public class TransferRequestDto {

    private Long assetId;
    private Long toLocationId;
    private String reason;
}
