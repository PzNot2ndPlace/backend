package ru.tbank.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.tbank.backend.config.userDetails.CustomUserDetails;
import ru.tbank.backend.dto.LocationRequestDto;
import ru.tbank.backend.dto.LocationsDto;
import ru.tbank.backend.service.impl.LocationService;

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

    @GetMapping()
    public LocationsDto getMethodName(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return locationService.getLocations(customUserDetails.getId());
    }
}   
