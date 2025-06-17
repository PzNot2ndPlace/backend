package ru.tbank.backend.service.impl;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.tbank.backend.dto.LocationDto;
import ru.tbank.backend.dto.LocationRequestDto;
import ru.tbank.backend.dto.LocationsForModelDto;
import ru.tbank.backend.dto.NoteProjection;
import ru.tbank.backend.entity.LocationEntity;
import ru.tbank.backend.repository.LocationRepository;
import ru.tbank.backend.repository.NoteRepository;
import ru.tbank.backend.service.NotificationService;

@Service
@RequiredArgsConstructor
@Slf4j
public class LocationService {
    private final LocationRepository locationRepository;
    private final NoteRepository noteRepository;
    private final NotificationService notificationService;

    private final double epsilon = 0.00049;

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

    @Transactional
    public void acceptCoords(UUID userId, String coords) {
        List<LocationEntity> locations = locationRepository.findAllByUserId(userId);
        for (LocationEntity l: locations) {
            if (isNear(l.getCoords(), coords)) {
                log.info("близко {}", coords);
                List<NoteProjection> notes = noteRepository.findLocationNotes(userId, l.getName());

                for (NoteProjection n: notes) {
                    StringBuilder message = new StringBuilder();
                    message.append("⏰ Напоминание: ").append(n.getText());

                    notificationService.sendNotification(
                        userId,
                        "Напоминание",
                        message.toString()
                    );
                }
            }
        }
    }

    private boolean isNear(String c1, String c2) {
        double[] c1Doubles = Arrays.stream(c1.split(" ")).mapToDouble(Double::parseDouble).toArray();
        double[] c2Doubles = Arrays.stream(c2.split(" ")).mapToDouble(Double::parseDouble).toArray();
        return distance(c1Doubles[0], c1Doubles[1], c2Doubles[0], c2Doubles[1]) <= epsilon;
    }

    private Double distance(double x1, double y1, double x2, double y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        return Math.sqrt(dx * dx + dy * dy);
    }
}
