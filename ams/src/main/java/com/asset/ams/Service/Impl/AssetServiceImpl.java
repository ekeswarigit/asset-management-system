package com.asset.ams.Service.Impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.asset.ams.Repository.AssetRepository;
import com.asset.ams.Repository.AssetTypeRepository;
import com.asset.ams.Repository.LocationRepository;
import com.asset.ams.Service.AssetService;
import com.asset.ams.Specification.AssetSpecification;
import com.asset.ams.dto.RequestDTO.AssetRequestDto;
import com.asset.ams.dto.Response.AssetResponseDto;
import com.asset.ams.mapper.AssetMapper;
import com.asset.ams.model.Asset;
import com.asset.ams.model.AssetType;
import com.asset.ams.model.Location;
import com.asset.ams.payload.AssetCondition;
import com.asset.ams.payload.AssetStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AssetServiceImpl implements AssetService {

    
    private final AssetRepository assetRepository;
    private final AssetTypeRepository assetTypeRepository;
    private final LocationRepository locationRepository;
    //private final Pageable pageable;

     @Override
    public AssetResponseDto create(AssetRequestDto dto) {

        AssetType type = assetTypeRepository.findById(dto.getTypeId())
                .orElseThrow(() -> new RuntimeException("Asset Type not found"));

        Location location = locationRepository.findById(dto.getLocationId())
                .orElseThrow(() -> new RuntimeException("Location not found"));

        Asset saved = assetRepository.save(AssetMapper.toEntity(dto, type, location));

        // Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // String userEmail = auth.getName();
        // saved.setCreatedBy(userEmail);

        return AssetMapper.toDto(saved);
    }

    @Override
    public AssetResponseDto update(Long id, AssetRequestDto dto) {

        Asset asset = assetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asset not found"));

        AssetType type = assetTypeRepository.findById(dto.getTypeId())
                .orElseThrow(() -> new RuntimeException("Asset Type not found"));

        Location location = locationRepository.findById(dto.getLocationId())
                .orElseThrow(() -> new RuntimeException("Location not found"));

        asset.setAssetName(dto.getAssetName());
        asset.setSerialNumber(dto.getSerialNumber());
        asset.setBrand(dto.getBrand());
        asset.setModel(dto.getModel());
        asset.setPurchaseDate(dto.getPurchaseDate());
        asset.setWarrantyExpiry(dto.getWarrantyExpiry());
        asset.setCost(dto.getCost());
        asset.setStatus(AssetStatus.valueOf(dto.getStatus().toUpperCase()));
        asset.setAssetCondition(AssetCondition.valueOf(dto.getAssetCondition().toUpperCase()));
        asset.setNotes(dto.getNotes());
        asset.setAssetType(type);
        asset.setLocation(location);

        return AssetMapper.toDto(assetRepository.save(asset));
    }

    @Override
    public void delete(Long id) {

        if(!assetRepository.existsById(id))
            throw new RuntimeException("Asset not found");

        assetRepository.deleteById(id);
    }

    @Override
    public AssetResponseDto getById(Long id) {

        Asset asset = assetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asset not found"));

        return AssetMapper.toDto(asset);
    }

    @Override
    public Page<AssetResponseDto> getAll( String keyword, AssetStatus status,  AssetCondition condition,int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Specification<Asset> spec = AssetSpecification.filterAssets(keyword, status, condition);

        return assetRepository.findAll(pageable).map(AssetMapper::toDto);
    }

    // @Override
    // public List<AssetResponseDto> getByAssetType(Long typeId) {
    //     return assetRepository.findByAssetTypeTypeId(typeId)
    //             .stream()
    //             .map(AssetMapper::toDto)
    //             .toList();
    // }
}

