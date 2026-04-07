package com.asset.ams.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.asset.ams.model.TransferHistory;

@Repository
public interface TransferHistoryRepository extends JpaRepository<TransferHistory,Long> {

    List<TransferHistory> findAllByOrderByTransferredAtDesc();
    List<TransferHistory> findByAssetAssetId(Long assetId);
}
