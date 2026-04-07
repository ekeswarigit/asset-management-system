package com.asset.ams.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.asset.ams.model.AssetTransferRequest;
import com.asset.ams.payload.TransferStatus;

@Repository
public interface AssetTransferRequestRepository extends JpaRepository<AssetTransferRequest,Long>{

    List<AssetTransferRequest> findByStatus(TransferStatus status);
    List<AssetTransferRequest> findByRequestedByUserId(Long userId);
    List<AssetTransferRequest> findByAssetAssetId(Long assetId);
}
