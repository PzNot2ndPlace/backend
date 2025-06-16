package ru.tbank.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.tbank.backend.dto.CreateUserDto;
import ru.tbank.backend.dto.RegistrationRequestDto;

@Mapper(componentModel = "spring")
public interface RegistrationMapper {

    CreateUserDto toCreateUserDto(RegistrationRequestDto requestDto);

    default CreateUserDto toCreateUserDtoWithPassword(RegistrationRequestDto requestDto, String password) {
        CreateUserDto dto = toCreateUserDto(requestDto);
        dto.setPassword(password);
        return dto;
    }
}