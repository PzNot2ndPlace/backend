package ru.tbank.backend.service;

import ru.tbank.backend.dto.LoginRequestDto;
import ru.tbank.backend.dto.RegistrationRequestDto;
import ru.tbank.backend.dto.TokenResponse;

public interface AuthService {

    TokenResponse login(LoginRequestDto loginRequest);

    TokenResponse register(RegistrationRequestDto registrationRequest);

    void logout();

}
