package com.asset.ams.mapper;

import com.asset.ams.dto.RequestDTO.LocationRequestDto;
import com.asset.ams.dto.Response.LocationResponseDto;
import com.asset.ams.model.Location;

public class LocationMapper {

     public static Location toEntity(LocationRequestDto dto) {
        Location loc = new Location();
        loc.setLocationName(dto.getLocationName());
        loc.setDescription(dto.getDescription());
        return loc;
    }

    public static LocationResponseDto toDto(Location loc) {
        return LocationResponseDto.builder()
                .locationId(loc.getLocationId())
                .locationName(loc.getLocationName())
                .description(loc.getDescription())
                .build();
    }
}
