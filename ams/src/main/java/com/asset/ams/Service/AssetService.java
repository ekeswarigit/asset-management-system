package com.asset.ams.Service;

import java.util.List;

import com.asset.ams.dto.RequestDTO.AssetRequestDto;
import com.asset.ams.dto.Response.AssetResponseDto;

public interface AssetService {

   AssetResponseDto create(AssetRequestDto dto);

    AssetResponseDto update(Long id, AssetRequestDto dto);

    void delete(Long id);

    AssetResponseDto getById(Long id);

    List<AssetResponseDto> getAll();

   // List<AssetResponseDto> getByAssetType(Long typeId);

}