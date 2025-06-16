package ru.tbank.backend.config.exceptions;

/**
 *  Ошибка выбрасывается, когда данные указаны неверно
 */
public class BadRequestException extends RuntimeException {
    /**
     * Создает BadRequestException
     *
     * @param message
     */
    public BadRequestException(String message) {
        super(message);
    }
}
