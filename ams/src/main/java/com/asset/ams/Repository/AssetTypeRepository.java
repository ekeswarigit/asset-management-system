package com.asset.ams.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.asset.ams.model.AssetType;

@Repository
public interface AssetTypeRepository extends JpaRepository<AssetType, Long> {

      boolean existsByTypeName(String typeName);
}
