package ru.tbank.backend.config.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.tbank.backend.config.exceptions.auth.RefreshTokenExpiredException;
import ru.tbank.backend.config.exceptions.auth.RefreshTokenNotFoundException;
import ru.tbank.backend.config.exceptions.auth.UserAlreadyExistsException;
import ru.tbank.backend.config.exceptions.auth.UserBadCredentialsException;
import ru.tbank.backend.dto.CustomFieldError;
import ru.tbank.backend.dto.Response;
import ru.tbank.backend.dto.ValidationResponse;

import java.time.LocalDateTime;
import java.util.List;



/**
 * Обработчик ошибок приложения
 */
@RestControllerAdvice
@Slf4j
public final class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        if (e.getMessage().contains("Cannot deserialize value of type")) {
            ValidationResponse errorResponse = new ValidationResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    LocalDateTime.now(),
                    "Валидация провалена",
                    List.of(new CustomFieldError(null, e.getMessage()))
            );
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new Response(
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                e.getMessage()
        ), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Response> handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        return new ResponseEntity<>(new Response(
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                e.getMessage()
        ), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ValidationResponse> handleMissingServletRequestParameterException(
            MissingServletRequestParameterException e) {
        ValidationResponse errorResponse = new ValidationResponse(
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                "Валидация провалена",
                List.of(new CustomFieldError(e.getParameterName(), e.getMessage()))
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PropertyReferenceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ValidationResponse> handlePropertyReferenceException(PropertyReferenceException e) {
        ValidationResponse errorResponse = new ValidationResponse(
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                "Валидация провалена",
                List.of(new CustomFieldError(e.getPropertyName(), e.getMessage()))
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Response> handleIllegalArgumentException(IllegalArgumentException e) {
        return new ResponseEntity<>(new Response(
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                e.getMessage()
        ), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Response> handleBadRequestException(BadRequestException e) {
        return new ResponseEntity<>(new Response(
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                e.getMessage()
        ), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserBadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Response> handleUserBadCredentialsException(UserBadCredentialsException e) {
        return new ResponseEntity<>(new Response(
                HttpStatus.UNAUTHORIZED.value(),
                LocalDateTime.now(),
                e.getMessage()
        ), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(RefreshTokenExpiredException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Response> handleRefreshTokenExpiredException(RefreshTokenExpiredException e) {
        return new ResponseEntity<>(new Response(
                HttpStatus.UNAUTHORIZED.value(),
                LocalDateTime.now(),
                e.getMessage()
        ), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handleUsernameNotFoundException(UsernameNotFoundException e) {
        return new ResponseEntity<>(new Response(
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now(),
                e.getMessage()
        ), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RefreshTokenNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handleUsernameNotFoundException(RefreshTokenNotFoundException e) {
        return new ResponseEntity<>(new Response(
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now(),
                e.getMessage()
        ), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<Response> handleForbiddenException(ForbiddenException e) {
        return new ResponseEntity<>(new Response(
                HttpStatus.FORBIDDEN.value(),
                LocalDateTime.now(),
                e.getMessage()
        ), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Response> handleException(Exception e) {
        log.error(e.getMessage(), e);

        return new ResponseEntity<>(new Response(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                LocalDateTime.now(),
                "Что-то пошло не так"
        ), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
