package com.asset.ams.Service.Impl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.asset.ams.Repository.AssetSubcategoryRepository;
import com.asset.ams.Repository.AssetTypeRepository;
import com.asset.ams.Service.AssetSubcategoryService;
import com.asset.ams.dto.RequestDTO.AssetSubcategoryRequestDto;
import com.asset.ams.dto.Response.AssetSubcategoryResponseDto;
import com.asset.ams.mapper.AssetSubcategoryMapper;
import com.asset.ams.model.AssetSubcategory;
import com.asset.ams.model.AssetType;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AssetSubcategoryServiceImpl implements AssetSubcategoryService {

    private final AssetSubcategoryRepository repository;
    private final AssetTypeRepository assetTypeRepository;

    @Override
    @Transactional
    public AssetSubcategoryResponseDto create(AssetSubcategoryRequestDto dto) {
        AssetType type = assetTypeRepository.findById(dto.getTypeId())
                .orElseThrow(() -> new RuntimeException("Asset Type not found with ID: " + dto.getTypeId()));
        
        if (repository.existsBySubcategoryNameAndAssetType_TypeId(dto.getSubcategoryName(), dto.getTypeId())) {
            throw new RuntimeException("Subcategory with name " + dto.getSubcategoryName() + " already exists in this type.");
        }

        AssetSubcategory subcategory = AssetSubcategoryMapper.toEntity(dto, type);
        return AssetSubcategoryMapper.toDto(repository.save(subcategory));
    }

    @Override
    @Transactional
    public AssetSubcategoryResponseDto update(Long id, AssetSubcategoryRequestDto dto) {
        AssetSubcategory subcategory = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subcategory not found with ID: " + id));

        AssetType type = assetTypeRepository.findById(dto.getTypeId())
                .orElseThrow(() -> new RuntimeException("Asset Type not found with ID: " + dto.getTypeId()));

        subcategory.setSubcategoryName(dto.getSubcategoryName());
        subcategory.setDescription(dto.getDescription());
        subcategory.setAssetType(type);

        return AssetSubcategoryMapper.toDto(repository.save(subcategory));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        AssetSubcategory subcategory = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subcategory not found with ID: " + id));
        subcategory.setDeleted(true);
        repository.save(subcategory);
    }

    @Override
    public AssetSubcategoryResponseDto getById(Long id) {
        AssetSubcategory subcategory = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subcategory not found with ID: " + id));
        return AssetSubcategoryMapper.toDto(subcategory);
    }

    @Override
    public List<AssetSubcategoryResponseDto> getAll() {
        return repository.findByDeletedFalse().stream()
                .map(AssetSubcategoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AssetSubcategoryResponseDto> getByTypeId(Long typeId) {
        return repository.findByAssetType_TypeIdAndDeletedFalse(typeId).stream()
                .map(AssetSubcategoryMapper::toDto)
                .collect(Collectors.toList());
    }
}
