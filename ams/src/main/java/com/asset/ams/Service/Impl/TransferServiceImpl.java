package com.asset.ams.Service.Impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.asset.ams.Repository.AssetRepository;
import com.asset.ams.Repository.AssetTransferRequestRepository;
import com.asset.ams.Repository.LocationRepository;
import com.asset.ams.Repository.TransferHistoryRepository;
import com.asset.ams.Repository.UserRepository;
import com.asset.ams.Service.TransferService;
import com.asset.ams.dto.RequestDTO.TransferRequestDto;
import com.asset.ams.dto.Response.TransferResponseDto;
import com.asset.ams.dto.TransferActionDto;
import com.asset.ams.mapper.TransferMapper;
import com.asset.ams.model.Asset;
import com.asset.ams.model.AssetTransferRequest;
import com.asset.ams.model.Location;
import com.asset.ams.model.TransferHistory;
import com.asset.ams.model.User;
import com.asset.ams.payload.TransferStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService{

    private final AssetRepository assetRepository;
    private final LocationRepository locationRepository;
    private final UserRepository userRepository;
    private final TransferHistoryRepository historyRepository;
    private final AssetTransferRequestRepository transferRepository;


    public TransferResponseDto createRequest(TransferRequestDto dto, String username) {

        Asset asset = assetRepository.findById(dto.getAssetId())
                .orElseThrow(() -> new RuntimeException("Asset not found"));

        Location toLocation = locationRepository.findById(dto.getToLocationId())
                .orElseThrow(() -> new RuntimeException("Location not found"));

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        AssetTransferRequest request = new AssetTransferRequest();
        request.setAsset(asset);
        request.setFromLocation(asset.getLocation());
        request.setToLocation(toLocation);
        request.setReason(dto.getReason());
        request.setRequestedBy(user);
        request.setStatus(TransferStatus.PENDING);
        request.setRequestedAt(LocalDateTime.now());

        transferRepository.save(request);

        return TransferMapper.toDto(request);
    }

    //  2. Approve / Reject (ADMIN)
    public String processRequest(Long id, TransferActionDto dto, String adminName) {

        AssetTransferRequest request = transferRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        User admin = userRepository.findByEmail(adminName)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        // ❗ prevent duplicate processing
        if (request.getStatus() != TransferStatus.PENDING) {
            throw new RuntimeException("Already processed");
        }

        request.setStatus(dto.getStatus());
        request.setApprovedBy(admin);
        request.setActionAt(LocalDateTime.now());

        //  If approved
        if (dto.getStatus() == TransferStatus.APPROVED) {

            Asset asset = request.getAsset();
            asset.setLocation(request.getToLocation());
            assetRepository.save(asset);

            // Save history
            TransferHistory history = new TransferHistory();
            history.setAsset(asset);
            history.setFromLocation(request.getFromLocation());
            history.setToLocation(request.getToLocation());
            history.setApprovedBy(admin);
            history.setReason(request.getReason());
            history.setTransferredAt(LocalDateTime.now());

            historyRepository.save(history);
        }

        transferRepository.save(request);

        return "Request " + dto.getStatus();
    }

     // Get all requests (Admin)
    public List<TransferResponseDto> getAllTransfers() {
        return transferRepository.findAllByOrderByRequestedAtDesc()
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }
    // Get current user's requests
    public List<TransferResponseDto> getMyTransfers(String username) {
        User user = userRepository.findByEmail(username)
                .orElseGet(() -> userRepository.findByuserName(username)
                .orElseThrow(() -> new RuntimeException("User not found")));
        return transferRepository.findByRequestedByUserId(user.getUserId())
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }
    // Get location history for an asset
    public List<TransferHistory> getLocationHistory(Long assetId) {
        return historyRepository.findByAssetAssetId(assetId);
    }
    public void deleteRequest(Long id) {
        transferRepository.deleteById(id);
    }
    private TransferResponseDto mapToResponseDto(AssetTransferRequest req) {
        return TransferResponseDto.builder()
                .atrid(req.getAtrid())
                .assetId(req.getAsset() != null ? req.getAsset().getAssetId() : null)
                .assetName(req.getAsset() != null ? req.getAsset().getAssetName() : "Unknown Asset")
                .fromLocation(req.getFromLocation() != null ? req.getFromLocation().getLocationName() : "None")
                .toLocation(req.getToLocation() != null ? req.getToLocation().getLocationName() : "Unknown Location")
                .status(req.getStatus() != null ? req.getStatus().name() : "PENDING")
                .requestedBy(req.getRequestedBy() != null ? req.getRequestedBy().getUserName() : "Unknown User")
                .build();
    }
}
