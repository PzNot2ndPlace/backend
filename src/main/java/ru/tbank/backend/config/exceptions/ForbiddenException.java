package ru.tbank.backend.config.exceptions;

/**
 *  Ошибка выбрасывается, когда у пользователя отсутствуют права доступа
 */
public class ForbiddenException extends RuntimeException {
    /**
     * Создает ForbiddenException
     */
    public ForbiddenException() {
        super("У вас нет прав доступа");
    }
}
