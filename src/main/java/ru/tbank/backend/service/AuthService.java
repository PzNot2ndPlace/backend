package ru.tbank.backend.service;

import ru.tbank.backend.dto.LoginRequestDto;
import ru.tbank.backend.dto.RegistrationRequestDto;

public interface AuthService {

    String login(LoginRequestDto loginRequest);

    String register(RegistrationRequestDto registrationRequest);

    void logout();

}
