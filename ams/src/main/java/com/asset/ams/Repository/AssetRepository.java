package com.asset.ams.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.asset.ams.dto.RequestDTO.AssetRequestDto;
import com.asset.ams.dto.Response.AssetResponseDto;
import com.asset.ams.model.Asset;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long>,JpaSpecificationExecutor<Asset> {

   // List<AssetResponseDto> findByAssetTypeTypeId(Long typeId);

    AssetResponseDto save(AssetRequestDto request);
}
