package com.asset.ams.Service;

import org.springframework.data.domain.Page;

import com.asset.ams.dto.RequestDTO.AssetRequestDto;
import com.asset.ams.dto.RequestDTO.AssignRequestDto;
import com.asset.ams.dto.RequestDTO.UnassignRequestDto;
import com.asset.ams.dto.Response.AssetResponseDto;
import com.asset.ams.dto.Response.AssignResponseDto;
import com.asset.ams.payload.AssetCondition;
import com.asset.ams.payload.AssetStatus;

public interface AssetService {

   AssetResponseDto create(AssetRequestDto dto);

   AssetResponseDto update(Long id, AssetRequestDto dto);

    void delete(Long id);

    AssetResponseDto getById(Long id);

    Page<AssetResponseDto> getAll( String keyword, AssetStatus status, AssetCondition condition, Long typeId, int page, int size);

    AssignResponseDto assignAsset(AssignRequestDto dto);

    AssignResponseDto unassignAsset(UnassignRequestDto dto);

  //  List<AssetResponseDto> getByAssetType(Long typeId);

}