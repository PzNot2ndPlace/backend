package ru.tbank.backend.config.exceptions.auth;

/**
 * Ошибка выбрасывается, когда пользователь использует неправльный(-ые) логин и/или пароль
 */
public class UserBadCredentialsException extends RuntimeException {

    /**
     * Ошибка выбрасывается, когда пользователь использует неправльный(-ые) логин и/или пароль
     */
    public UserBadCredentialsException() {
        super("Неверный логин и/или пароль.");
    }

    /**
     * Ошибка выбрасывается, когда пользователь использует неправльный(-ые) логин и/или пароль
     */
    public UserBadCredentialsException(Throwable exception) {
        super("Неверный логин и/или пароль.", exception);
    }
}
