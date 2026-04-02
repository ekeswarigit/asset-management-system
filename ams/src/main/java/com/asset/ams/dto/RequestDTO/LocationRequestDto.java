package com.asset.ams.dto.RequestDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LocationRequestDto {

    @NotBlank(message = "Location name required")
    private String locationName;

    private String description;
    
    private Double latitude;
    private Double longitude;

}
