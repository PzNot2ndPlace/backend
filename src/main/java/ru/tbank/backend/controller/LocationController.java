package ru.tbank.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.tbank.backend.config.userDetails.CustomUserDetails;
import ru.tbank.backend.dto.LocationDto;
import ru.tbank.backend.dto.LocationRequestDto;
import ru.tbank.backend.dto.LocationsForModelDto;
import ru.tbank.backend.service.impl.LocationService;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequiredArgsConstructor
@RequestMapping("/location")
public class LocationController {

    private final LocationService locationService;

    @PostMapping()
    public void add(
        @RequestBody LocationRequestDto request,
        @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        locationService.addLocation(request, customUserDetails.getId());
    }

    @GetMapping("for/model")
    public LocationsForModelDto getLocationsForModel(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return locationService.getLocationsForModel(customUserDetails.getId());
    }

    @GetMapping()
    public List<LocationDto>  getLocations(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return locationService.getLocations(customUserDetails.getId());
    }
}
