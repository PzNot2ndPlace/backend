package ru.tbank.backend.config.exceptions.auth;

/**
 * Ошибка выбрасывается, когда истек срок действия refresh-токена
 */
public class RefreshTokenExpiredException extends RuntimeException {

    /**
     * Ошибка выбрасывается, когда истек срок действия refresh-токена
     */
    public RefreshTokenExpiredException() {
        super("Истек срок действия refresh-токена. Необходимо ввести логин и пароль.");
    }
}
