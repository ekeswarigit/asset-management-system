package com.asset.ams.Controller;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.asset.ams.Service.AssetService;
import com.asset.ams.dto.ApiResponse;
import com.asset.ams.dto.RequestDTO.AssetRequestDto;
import com.asset.ams.dto.RequestDTO.AssignRequestDto;
import com.asset.ams.dto.RequestDTO.UnassignRequestDto;
import com.asset.ams.dto.Response.AssetResponseDto;
import com.asset.ams.dto.Response.AssignResponseDto;
import com.asset.ams.payload.AssetCondition;
import com.asset.ams.payload.AssetStatus;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.asset.ams.Service.TransferService;
import com.asset.ams.dto.Response.TransferHistoryDto;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/assets")
@RequiredArgsConstructor
public class AssetController {

    private final AssetService assetService;
    private final TransferService transferService;

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
    public ApiResponse<Page<AssetResponseDto>> getAll(
         @RequestParam(required = false) String keyword,
         @RequestParam(required = false) AssetStatus status,
         @RequestParam(required = false) AssetCondition condition,
         @RequestParam(required = false) Long typeId,
         @RequestParam(defaultValue = "0") int page,
         @RequestParam(defaultValue = "5") int size) {

        return ApiResponse.<Page<AssetResponseDto>>builder().success(true).message("Assets fetched successfully")
                .data(assetService.getAll(keyword,status,condition,typeId,page, size)).errorCode(0).timestamp(LocalDateTime.now()).build();
    }

    @PostMapping("/assign")
    public ApiResponse<AssignResponseDto> assignAsset(@RequestBody AssignRequestDto dto) {
        return ApiResponse.<AssignResponseDto>builder().success(true).
               message("Assets Assigned successfully")
               .data(assetService.assignAsset(dto)).errorCode(0).timestamp(LocalDateTime.now()).build();
    }

    @PostMapping("/unassign")
    public ApiResponse<AssignResponseDto> unassignAsset(@RequestBody UnassignRequestDto dto) {
        return ApiResponse.<AssignResponseDto>builder()
                .success(true).message("Asset unassigned successfully")
                .data(assetService.unassignAsset(dto)).errorCode(0).timestamp(LocalDateTime.now()).build();
    }
    // @GetMapping("/type/{typeId}")
    // public ApiResponse<List<AssetResponseDto>> getByType(@Valid @PathVariable Long typeId) {

    //     return ApiResponse.<List<AssetResponseDto>>builder().success(true) .message("Assets fetched by type")
    //             .data(assetService.getByAssetType(typeId))
    //              .errorCode(0).timestamp(LocalDateTime.now()) .build();
    // }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<AssetResponseDto> create(
            @Valid @RequestPart("data") AssetRequestDto dto,
            @RequestPart(value = "image", required = false) MultipartFile image) {

        return ApiResponse.<AssetResponseDto>builder()
                .success(true).message("Asset created successfully")
                .data(assetService.create(dto, image))
                .errorCode(0).timestamp(LocalDateTime.now()).build();
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<AssetResponseDto> update(
            @PathVariable Long id,
            @RequestPart("data") AssetRequestDto dto,
            @RequestPart(value = "image", required = false) MultipartFile image) {

        return ApiResponse.<AssetResponseDto>builder()
                .success(true).message("Asset updated successfully")
                .data(assetService.update(id, dto, image))
                .errorCode(0).timestamp(LocalDateTime.now()).build();
    }

    @GetMapping("/{id}/location-history")
    public ApiResponse<List<TransferHistoryDto>> getLocationHistory(@PathVariable Long id) {
        List<TransferHistoryDto> history = transferService.getLocationHistory(id).stream()
            .map(h -> TransferHistoryDto.builder()
                .thid(h.getThid())
                .reason(h.getReason())
                .transferredAt(h.getTransferredAt())
                .fromLocation(h.getFromLocation() != null ? new TransferHistoryDto.LocationDto(h.getFromLocation().getLocationName()) : null)
                .toLocation(h.getToLocation() != null ? new TransferHistoryDto.LocationDto(h.getToLocation().getLocationName()) : null)
                .approvedBy(h.getApprovedBy() != null ? new TransferHistoryDto.UserDto(h.getApprovedBy().getUserName()) : null)
                .build())
            .collect(Collectors.toList());

        return ApiResponse.<List<TransferHistoryDto>>builder()
                .success(true)
                .message("Location history fetched successfully")
                .data(history)
                .errorCode(0)
                .timestamp(LocalDateTime.now())
                .build();
    }
}