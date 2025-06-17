package ru.tbank.backend.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ru.tbank.backend.dto.LocationDto;
import ru.tbank.backend.dto.LocationRequestDto;
import ru.tbank.backend.dto.LocationsForModelDto;
import ru.tbank.backend.entity.LocationEntity;
import ru.tbank.backend.repository.LocationRepository;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;

    @Transactional
    public void addLocation(LocationRequestDto request, UUID userId) {
        LocationEntity location = new LocationEntity(null, userId, request.getName(), request.getCoords());
        locationRepository.save(location);
    }

    @Transactional
    public LocationsForModelDto getLocationsForModel(UUID userId) {

        List<LocationEntity> locations = locationRepository.findAllByUserId(userId);
        List<String> result = new LinkedList<>();
        for (LocationEntity e : locations) {
            result.add(e.getName());
        }
        return new LocationsForModelDto(result);
    }

    @Transactional
    public List<LocationDto> getLocations(UUID userId) {

        List<LocationEntity> locations = locationRepository.findAllByUserId(userId);
        List<LocationDto> result = new LinkedList<>();
        for (LocationEntity e : locations) {
            result.add(new LocationDto(e.getId(), e.getName(), e.getCoords()));
        }
        return result;
    }
}
