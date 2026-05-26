package com.asset.ams.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.asset.ams.model.AssetSubcategory;

@Repository
public interface AssetSubcategoryRepository extends JpaRepository<AssetSubcategory, Long> {

    List<AssetSubcategory> findByAssetType_TypeIdAndDeletedFalse(Long typeId);
    List<AssetSubcategory> findByDeletedFalse();
    boolean existsBySubcategoryNameAndAssetType_TypeId(String subcategoryName, Long typeId);
}
