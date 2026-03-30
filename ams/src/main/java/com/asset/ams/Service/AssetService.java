package com.asset.ams.Service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.asset.ams.dto.RequestDTO.AssetRequestDto;
import com.asset.ams.dto.Response.AssetResponseDto;

public interface AssetService {

   AssetResponseDto create(AssetRequestDto dto);

    AssetResponseDto update(Long id, AssetRequestDto dto);

    void delete(Long id);

    AssetResponseDto getById(Long id);

    Page<AssetResponseDto> getAll(int page, int size);

  //  List<AssetResponseDto> getByAssetType(Long typeId);

}