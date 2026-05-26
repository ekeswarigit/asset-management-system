package com.asset.ams.Service;

import java.util.List;
import com.asset.ams.dto.RequestDTO.AssetSubcategoryRequestDto;
import com.asset.ams.dto.Response.AssetSubcategoryResponseDto;

public interface AssetSubcategoryService {

    AssetSubcategoryResponseDto create(AssetSubcategoryRequestDto dto);
    AssetSubcategoryResponseDto update(Long id, AssetSubcategoryRequestDto dto);
    void delete(Long id);
    AssetSubcategoryResponseDto getById(Long id);
    List<AssetSubcategoryResponseDto> getAll();
    List<AssetSubcategoryResponseDto> getByTypeId(Long typeId);
}
