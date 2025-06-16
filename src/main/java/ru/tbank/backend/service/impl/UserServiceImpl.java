package ru.tbank.backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tbank.backend.config.exceptions.auth.UserAlreadyExistsException;
import ru.tbank.backend.config.exceptions.auth.UserNotFoundException;
import ru.tbank.backend.dto.CreateUserDto;
import ru.tbank.backend.dto.UserDto;
import ru.tbank.backend.entity.UserEntity;
import ru.tbank.backend.mapper.UserMapper;
import ru.tbank.backend.repository.UserRepository;
import ru.tbank.backend.service.UserService;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    @Override
    public void createUser(CreateUserDto userCreateDto) {
        if (userRepository.existsByEmail(userCreateDto.getEmail())) {
            throw new UserAlreadyExistsException();
        }

        UserEntity userEntity = userMapper.createDtoToEntity(userCreateDto);

        userMapper.entityToDto(userRepository.save(userEntity));
    }

    @Transactional(readOnly = true)
    @Override
    public UserDto getUser(UUID userId) {
        return userMapper.entityToDto(getRawUser(userId));
    }

    private UserEntity getRawUser(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }
}