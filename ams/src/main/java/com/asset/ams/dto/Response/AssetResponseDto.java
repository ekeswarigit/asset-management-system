package com.asset.ams.dto.Response;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AssetResponseDto {

    private Long assetId; 
    private String assetName;
    private String serialNumber;

    private String brand;
    private String model;

    private LocalDate purchaseDate;
    private LocalDate warrantyExpiry;

    private BigDecimal cost;
    
    private String status;     
    private String assetCondition;  
    private String notes;

    private String assetTypeName;
    private String locationName;
}
