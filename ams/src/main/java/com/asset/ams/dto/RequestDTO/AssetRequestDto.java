package com.asset.ams.dto.RequestDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AssetRequestDto {

    @NotBlank(message = "Asset name is required")
    @Size(min = 3, max = 100)
    private String assetName;

    @NotBlank(message = "Serial number is required")
    private String serialNumber;

    @NotBlank(message = "Brand is required")
    private String brand;

    private String model;

    @PastOrPresent(message = "Purchase date cannot be future")
    private LocalDate purchaseDate;

    private LocalDate warrantyExpiry;

    @Positive(message = "Cost must be positive")
    private BigDecimal cost;

    @NotBlank(message = "Status is required")
    private String status;

    private String assetCondition;
    private String notes;

    @NotNull(message = "Asset type is required")
    private Long typeId;

    @NotNull(message = "Location is required")
    private Long locationId;
}
