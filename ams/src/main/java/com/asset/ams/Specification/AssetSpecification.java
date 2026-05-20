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
            String keyword, AssetStatus status, AssetCondition condition, Long typeId) {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            // 🔍 Search (keyword)
            if (keyword != null && !keyword.isEmpty()) {
                String like = "%" + keyword.toLowerCase() + "%";

                List<Predicate> keywordPredicates = new ArrayList<>();
                keywordPredicates.add(cb.like(cb.lower(root.get("assetName")),    like));
                keywordPredicates.add(cb.like(cb.lower(root.get("brand")),        like));
                keywordPredicates.add(cb.like(cb.lower(root.get("model")),        like));

                // Smartly extract numeric suffix for generated asset codes (e.g. HCHE001 -> 1)
                String numericPart = keyword.trim().replaceAll("^.*?(\\d+)$", "$1");
                if (numericPart.matches("\\d+")) {
                    try {
                        Long idVal = Long.parseLong(numericPart);
                        keywordPredicates.add(cb.equal(root.get("assetId"), idVal));
                    } catch (NumberFormatException ignored) {}
                }

                predicates.add(cb.or(keywordPredicates.toArray(new Predicate[0])));
            }
            //  Filter - Status
            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }

            //  Filter - Condition
            if (condition != null) {
                predicates.add(cb.equal(root.get("assetCondition"), condition));
            }
            //  Filter - Asset Type
            if (typeId != null) {
                predicates.add(cb.equal(root.get("assetType").get("typeId"), typeId));
            }

            predicates.add(cb.isFalse(root.get("deleted")));
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}