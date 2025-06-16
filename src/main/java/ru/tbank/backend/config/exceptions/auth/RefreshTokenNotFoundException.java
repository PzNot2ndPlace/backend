package ru.tbank.backend.config.exceptions.auth;

/**
 * Ошибка выбрасывается, когда refresh-токен не был найден
 */
public class RefreshTokenNotFoundException extends RuntimeException {

    /**
     * Ошибка выбрасывается, когда refresh-токен не был найден
     */
    public RefreshTokenNotFoundException() {
        super("Refresh-токен не найден.");
    }
}
