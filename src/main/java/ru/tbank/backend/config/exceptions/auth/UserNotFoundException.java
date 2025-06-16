package ru.tbank.backend.config.exceptions.auth;

import java.util.UUID;

/**
 *  Ошибка выбрасывается, когда нужный пользователь не был найден
 */
public class UserNotFoundException extends RuntimeException {
    /**
     * Создает FileNotFoundException
     *
     * @param userId
     */
    public UserNotFoundException(UUID userId) {
        super(String.format("Пользователь с id: %s не найден", userId));
    }
}
