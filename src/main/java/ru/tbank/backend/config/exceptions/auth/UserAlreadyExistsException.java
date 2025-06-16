package ru.tbank.backend.config.exceptions.auth;

/**
 *  Ошибка выбрасывается, когда пользователь уже есть
 */
public class UserAlreadyExistsException extends RuntimeException  {

    public UserAlreadyExistsException() {
        super("Пользователь с такой почтой уже существует");
    }
}
