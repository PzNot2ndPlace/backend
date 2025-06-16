package ru.tbank.backend.service;

import org.springframework.security.core.userdetails.UserDetails;
import ru.tbank.backend.config.userDetails.CustomUserDetails;

public interface JwtService {
    String extractUserName(String token);

    String generateToken(CustomUserDetails user);

    boolean isTokenValid(String token, UserDetails userDetails);

    void banToken(String token);
}