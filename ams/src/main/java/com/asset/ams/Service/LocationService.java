package com.asset.ams.Service;

import java.util.List;

import com.asset.ams.dto.RequestDTO.LocationRequestDto;
import com.asset.ams.dto.Response.LocationResponseDto;

public interface LocationService {

     LocationResponseDto create(LocationRequestDto dto);

    LocationResponseDto update(Long id, LocationRequestDto dto);

    void delete(Long id);

    LocationResponseDto getById(Long id);

    List<LocationResponseDto> getAll();
}
