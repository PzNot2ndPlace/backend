package ru.tbank.backend.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tbank.backend.config.exceptions.BadRequestException;
import ru.tbank.backend.config.userDetails.CustomUserDetails;
import ru.tbank.backend.config.userDetails.CustomUserDetailsService;
import ru.tbank.backend.dto.CreateUserDto;
import ru.tbank.backend.dto.LoginRequestDto;
import ru.tbank.backend.dto.RegistrationRequestDto;
import ru.tbank.backend.mapper.RegistrationMapper;
import ru.tbank.backend.service.AuthService;
import ru.tbank.backend.service.JwtService;
import ru.tbank.backend.service.UserService;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final RegistrationMapper registrationMapper;

    @Transactional
    public String register(RegistrationRequestDto registrationRequest) {
        String hashedPassword = passwordEncoder.encode(registrationRequest.getPassword());

        CreateUserDto userCreateDto = registrationMapper.toCreateUserDtoWithPassword(
                registrationRequest,
                hashedPassword
        );

        userService.createUser(userCreateDto);

        return login(
                LoginRequestDto.builder()
                        .email(registrationRequest.getEmail())
                        .password(registrationRequest.getPassword())
                        .build()
        );
    }

    @Transactional
    public String login(LoginRequestDto request) {
        CustomUserDetails userDetails = customUserDetailsService.loadUserByUsername(request.getEmail());

        if (!passwordEncoder.matches(request.getPassword(), userDetails.getPassword())) {
            throw new BadRequestException("Неверный логин или пароль");
        }

        return jwtService.generateToken(userDetails);
    }

    @Transactional
    public void logout() {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getCredentials() != null) {
            String token = authentication.getCredentials().toString();
            jwtService.banToken(token);
        }
    }
}

