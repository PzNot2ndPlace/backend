package ru.tbank.backend.mapper;

import org.mapstruct.Mapper;
import ru.tbank.backend.dto.CreateUserDto;
import ru.tbank.backend.dto.UserDto;
import ru.tbank.backend.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserEntity createDtoToEntity(CreateUserDto userCreateDto);

    UserDto entityToDto(UserEntity userEntity);

}
