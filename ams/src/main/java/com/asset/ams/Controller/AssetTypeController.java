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
import com.asset.ams.Service.AssetTypeService;
import com.asset.ams.dto.ApiResponse;
import com.asset.ams.dto.RequestDTO.AssetTypeRequestDto;
import com.asset.ams.dto.Response.AssetTypeResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/asset-types")
@RequiredArgsConstructor
public class AssetTypeController {

    private final AssetTypeService service;

    @PostMapping
    public ApiResponse<AssetTypeResponseDto> create(@RequestBody AssetTypeRequestDto dto) {

        return ApiResponse.<AssetTypeResponseDto>builder()
                .success(true)
                .message("Asset Type created successfully")
                .data(service.create(dto))
                .errorCode(0)
                .timestamp(LocalDateTime.now())
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<AssetTypeResponseDto> update(@PathVariable Long id,
                                                    @RequestBody AssetTypeRequestDto dto) {

        return ApiResponse.<AssetTypeResponseDto>builder()
                .success(true)
                .message("Asset Type updated successfully")
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
                .message("Asset Type deleted successfully")
                .data("Deleted ID: " + id)
                .errorCode(0)
                .timestamp(LocalDateTime.now())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<AssetTypeResponseDto> getById(@PathVariable Long id) {

        return ApiResponse.<AssetTypeResponseDto>builder()
                .success(true)
                .message("Asset Type fetched successfully")
                .data(service.getById(id))
                .errorCode(0)
                .timestamp(LocalDateTime.now())
                .build();
    }

    @GetMapping
    public ApiResponse<List<AssetTypeResponseDto>> getAll() {

        return ApiResponse.<List<AssetTypeResponseDto>>builder()
                .success(true)
                .message("Asset Types fetched successfully")
                .data(service.getAll())
                .errorCode(0)
                .timestamp(LocalDateTime.now())
                .build();
    }
}    