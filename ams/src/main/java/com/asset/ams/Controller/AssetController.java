package com.asset.ams.Controller;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.asset.ams.Service.AssetService;
import com.asset.ams.dto.ApiResponse;
import com.asset.ams.dto.RequestDTO.AssetRequestDto;
import com.asset.ams.dto.Response.AssetResponseDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
@RestController
@RequestMapping("/api/assets")
@RequiredArgsConstructor
public class AssetController {

    private final AssetService assetService;

    @PostMapping
    public ApiResponse<AssetResponseDto> create(@Valid @RequestBody AssetRequestDto dto) {

        return ApiResponse.<AssetResponseDto>builder().success(true).message("Asset created successfully")
                .data(assetService.create(dto)).errorCode(0).timestamp(LocalDateTime.now()).build();
    }

    @PutMapping("/{id}")
    public ApiResponse<AssetResponseDto> update(@Valid @PathVariable Long id,
                                                @RequestBody AssetRequestDto dto) {

        return ApiResponse.<AssetResponseDto>builder().success(true).message("Asset updated successfully")
                .data(assetService.update(id, dto)) .errorCode(0).timestamp(LocalDateTime.now()).build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@Valid @PathVariable Long id) {

        assetService.delete(id);

        return ApiResponse.<String>builder().success(true).message("Asset deleted successfully")
                .data("Deleted ID: " + id) .errorCode(0) .timestamp(LocalDateTime.now()).build();
    }

    @GetMapping("/{id}")
    public ApiResponse<AssetResponseDto> getById(@Valid @PathVariable Long id) {

        return ApiResponse.<AssetResponseDto>builder().success(true).message("Asset fetched successfully")
                .data(assetService.getById(id)).errorCode(0).timestamp(LocalDateTime.now()) .build();
    }

    @GetMapping
    public ApiResponse<Page<AssetResponseDto>> getAll(@RequestParam(required = false) Long id,
         @RequestParam(defaultValue = "0") int page,
         @RequestParam(defaultValue = "5") int size) {

        return ApiResponse.<Page<AssetResponseDto>>builder().success(true).message("Assets fetched successfully")
                .data(assetService.getAll(page, size)).errorCode(0).timestamp(LocalDateTime.now()).build();
    }

    // @GetMapping("/type/{typeId}")
    // public ApiResponse<List<AssetResponseDto>> getByType(@Valid @PathVariable Long typeId) {

    //     return ApiResponse.<List<AssetResponseDto>>builder().success(true) .message("Assets fetched by type")
    //             .data(assetService.getByAssetType(typeId))
    //              .errorCode(0).timestamp(LocalDateTime.now()) .build();
    // }
}