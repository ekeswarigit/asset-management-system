package com.asset.ams.Service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.asset.ams.dto.RequestDTO.AssetRequestDto;
import com.asset.ams.dto.RequestDTO.AssignRequestDto;
import com.asset.ams.dto.Response.AssetResponseDto;
import com.asset.ams.dto.Response.AssignResponseDto;
import com.asset.ams.payload.AssetCondition;
import com.asset.ams.payload.AssetStatus;

public interface AssetService {

   AssetResponseDto create(AssetRequestDto dto);

    AssetResponseDto update(Long id, AssetRequestDto dto);

    void delete(Long id);

    AssetResponseDto getById(Long id);

    Page<AssetResponseDto> getAll( String keyword,AssetStatus status, AssetCondition condition,int page, int size);

    AssignResponseDto assignAsset(AssignRequestDto dto);

  //  List<AssetResponseDto> getByAssetType(Long typeId);

}