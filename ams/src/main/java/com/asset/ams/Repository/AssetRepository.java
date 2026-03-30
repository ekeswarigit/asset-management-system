package com.asset.ams.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.asset.ams.dto.RequestDTO.AssetRequestDto;
import com.asset.ams.dto.Response.AssetResponseDto;
import com.asset.ams.model.Asset;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {

    List<AssetResponseDto> findByAssetTypeTypeId(Long typeId);

    AssetResponseDto save(AssetRequestDto request);
}
