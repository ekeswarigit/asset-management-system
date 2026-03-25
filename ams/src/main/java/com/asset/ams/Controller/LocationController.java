package com.asset.ams.Controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asset.ams.Service.LocationService;
import com.asset.ams.dto.ApiResponse;
import com.asset.ams.dto.RequestDTO.LocationRequestDto;
import com.asset.ams.dto.Response.LocationResponseDto;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService service;

    @PostMapping
    public ApiResponse<LocationResponseDto> create(@RequestBody LocationRequestDto dto) {

        return ApiResponse.<LocationResponseDto>builder()
                .success(true)
                .message("Location created successfully")
                .data(service.create(dto))
                .errorCode(0)
                .timestamp(LocalDateTime.now())
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<LocationResponseDto> update(@PathVariable Long id,
                                                   @RequestBody LocationRequestDto dto) {

        return ApiResponse.<LocationResponseDto>builder()
                .success(true)
                .message("Location updated successfully")
                .data(service.update(id, dto))
                .errorCode(0)
                .timestamp(LocalDateTime.now())
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable Long id) {

        service.delete(id);

        return ApiResponse.<String>builder()
                .success(true)
                .message("Location deleted successfully")
                .data("Deleted ID: " + id)
                .errorCode(0)
                .timestamp(LocalDateTime.now())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<LocationResponseDto> getById(@PathVariable Long id) {

        return ApiResponse.<LocationResponseDto>builder()
                .success(true)
                .message("Location fetched successfully")
                .data(service.getById(id))
                .errorCode(0)
                .timestamp(LocalDateTime.now())
                .build();
    }

    @GetMapping
    public ApiResponse<List<LocationResponseDto>> getAll() {

        return ApiResponse.<List<LocationResponseDto>>builder()
                .success(true)
                .message("Locations fetched successfully")
                .data(service.getAll())
                .errorCode(0)
                .timestamp(LocalDateTime.now())
                .build();
    }
}