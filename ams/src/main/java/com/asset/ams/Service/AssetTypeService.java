package com.asset.ams.Service;

import java.util.List;

import com.asset.ams.dto.RequestDTO.AssetTypeRequestDto;
import com.asset.ams.dto.Response.AssetTypeResponseDto;

public interface AssetTypeService {

    AssetTypeResponseDto create(AssetTypeRequestDto dto);

    AssetTypeResponseDto update(Long id, AssetTypeRequestDto dto);

    void delete(Long id);

    AssetTypeResponseDto getById(Long id);

    List<AssetTypeResponseDto> getAll();
}       
