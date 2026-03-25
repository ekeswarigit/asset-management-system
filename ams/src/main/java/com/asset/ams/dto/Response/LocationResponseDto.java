package com.asset.ams.dto.Response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LocationResponseDto {

    private Long locationId;
    private String locationName;

    private String description;
}
