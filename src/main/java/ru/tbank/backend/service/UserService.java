package ru.tbank.backend.service;

import ru.tbank.backend.dto.CreateUserDto;
import ru.tbank.backend.dto.UserDto;

import java.util.UUID;

public interface UserService {

    void createUser(CreateUserDto userCreateDto);

    UserDto getUser(UUID userId);

}