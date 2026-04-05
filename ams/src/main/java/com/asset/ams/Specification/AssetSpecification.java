package com.asset.ams.Specification;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.asset.ams.model.Asset;
import com.asset.ams.payload.AssetCondition;
import com.asset.ams.payload.AssetStatus;

import jakarta.persistence.criteria.Predicate;

public class AssetSpecification {

    public static Specification<Asset> filterAssets(
            String keyword,
            AssetStatus status,
            AssetCondition condition
    ) {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            // 🔍 Search (keyword)
            if (keyword != null && !keyword.isEmpty()) {
                Predicate name = cb.like(cb.lower(root.get("assetName")), "%" + keyword.toLowerCase() + "%");
                Predicate brand = cb.like(cb.lower(root.get("brand")), "%" + keyword.toLowerCase() + "%");
                Predicate model = cb.like(cb.lower(root.get("model")), "%" + keyword.toLowerCase() + "%");
                Predicate serial = cb.like(cb.lower(root.get("serialNumber")), "%" + keyword.toLowerCase() + "%");
                
                predicates.add(cb.or(name, brand, model, serial));
            }

            // 🎯 Filter - Status
            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }

            // 🎯 Filter - Condition
            if (condition != null) {
                predicates.add(cb.equal(root.get("assetCondition"), condition));
            }
            predicates.add(cb.equal(root.get("deleted"), false));
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}