package ru.tbank.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tbank.backend.dto.LoginRequestDto;
import ru.tbank.backend.dto.RegistrationRequestDto;
import ru.tbank.backend.service.AuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Авторизация", description = "Контроллер, отвечающий за авторизацию")
public class AuthController {

    private final AuthService authService;

    @Operation(
            summary = "Регистрация",
            description = "Позволяет пользователю зарегистрироваться в системе"
    )
    @PostMapping("/registration")
    private String registration(
            @RequestBody @Valid RegistrationRequestDto registrationRequest
    ) {
        return authService.register(registrationRequest);
    }

    @Operation(
            summary = "Вход в учетную запись",
            description = "Позволяет пользователю войти в учетную запись"
    )
    @PostMapping("/login")
    private String login(
            @RequestBody @Valid LoginRequestDto loginRequest
    ) {
        return authService.login(loginRequest);
    }

    @Operation(
            summary = "Выход из учетной записи",
            description = "Позволяет пользователю выйти из учетной записи"
    )
    @PostMapping("/logout")
    private void logout() {
        authService.logout();
    }
}