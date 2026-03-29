package com.asset.ams.Service.Impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.asset.ams.Repository.LocationRepository;
import com.asset.ams.Service.LocationService;
import com.asset.ams.dto.RequestDTO.LocationRequestDto;
import com.asset.ams.dto.Response.LocationResponseDto;
import com.asset.ams.mapper.LocationMapper;
import com.asset.ams.model.Location;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final SoftDeleteServiceImpl softDeleteServiceImpl;
    private final LocationRepository repository;


    @Override
    public LocationResponseDto create(LocationRequestDto dto) {

        if(repository.existsByLocationName(dto.getLocationName()))
            throw new RuntimeException("Location already exists");

        Location saved = repository.save(LocationMapper.toEntity(dto));

        return LocationMapper.toDto(saved);
    }

    @Override
    public LocationResponseDto update(Long id, LocationRequestDto dto) {

        Location loc = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Location not found"));

        loc.setLocationName(dto.getLocationName());
        loc.setDescription(dto.getDescription());

        return LocationMapper.toDto(repository.save(loc));
    }

    @Override
    public LocationResponseDto getById(Long id) {

        Location loc = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Location not found"));

        if (loc.isDeleted()) {
        throw new RuntimeException("Location is deleted");
        }

        return LocationMapper.toDto(loc);
    }

    @Override
    public List<LocationResponseDto> getAll() {

        return repository.findAll()
                .stream()
                .filter(location -> !location.isDeleted())
                .map(LocationMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {

        Location location = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Location not found"));

        // ✅ Soft delete
        softDeleteServiceImpl.softDelete(location, "admin");
        repository.save(location);
    }
   
}
