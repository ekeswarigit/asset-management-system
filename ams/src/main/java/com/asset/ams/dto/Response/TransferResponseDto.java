package com.asset.ams.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransferResponseDto {

    private Long atrid;
    private String assetName;
    private String fromLocation;
    private String toLocation;
    private String status;
    private String requestedBy;
}
