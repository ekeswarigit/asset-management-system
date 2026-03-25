package com.asset.ams.Service.Impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.asset.ams.Repository.AssetTypeRepository;
import com.asset.ams.Service.AssetTypeService;
import com.asset.ams.dto.RequestDTO.AssetTypeRequestDto;
import com.asset.ams.dto.Response.AssetTypeResponseDto;
import com.asset.ams.mapper.AssetTypeMapper;
import com.asset.ams.model.AssetType;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AssetTypeServiceImpl implements AssetTypeService {

    private final AssetTypeRepository repository;

    @Override
    public AssetTypeResponseDto create(AssetTypeRequestDto dto) {

        if(repository.existsByTypeName(dto.getTypeName()))
            throw new RuntimeException("Asset type already exists");

        AssetType saved = repository.save(AssetTypeMapper.toEntity(dto));

        return AssetTypeMapper.toDto(saved);
    }

    @Override
    public AssetTypeResponseDto update(Long id, AssetTypeRequestDto dto) {

        AssetType type = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asset type not found"));

        type.setTypeName(dto.getTypeName());
        type.setDescription(dto.getDescription());

        return AssetTypeMapper.toDto(repository.save(type));
    }

    @Override
    public void delete(Long id) {

        if(!repository.existsById(id))
            throw new RuntimeException("Asset type not found");

        repository.deleteById(id);
    }

    @Override
    public AssetTypeResponseDto getById(Long id) {

        AssetType type = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asset type not found"));

        return AssetTypeMapper.toDto(type);
    }

    @Override
    public List<AssetTypeResponseDto> getAll() {

        return repository.findAll()
                .stream()
                .map(AssetTypeMapper::toDto)
                .toList();
    }
}


