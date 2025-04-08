package com.example.geospatialserver.exception;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;

import static com.example.geospatialserver.util.ExceptionStringUtil.BAD_REQUEST_ERROR_TITLE;
import static com.example.geospatialserver.util.ExceptionStringUtil.INTERNAL_SERVER_ERROR_ERROR_TITLE;
import static com.example.geospatialserver.util.ExceptionStringUtil.NOT_FOUND_ERROR_TITLE;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {
    private void logTheException(Exception e) {
        log.error("Exception: {} handled normally. Message: {}", e.getClass().getName(), e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApplicationError> handleValidationExceptions(MethodArgumentNotValidException e) {
        logTheException(e);
        List<String> errors = new ArrayList<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            if (error instanceof FieldError fieldError) {
                errors.add(fieldError.getField() + " : " + fieldError.getDefaultMessage());
            } else {
                errors.add(error.getDefaultMessage());
            }
        });
        var applicationError = new ApplicationError(BAD_REQUEST_ERROR_TITLE, errors);
        return new ResponseEntity<>(applicationError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApplicationError> handleIllegalArgumentExceptionException(IllegalArgumentException e) {
        logTheException(e);
        var ApplicationError = new ApplicationError(BAD_REQUEST_ERROR_TITLE, e.getMessage());
        return new ResponseEntity<>(ApplicationError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApplicationError> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        logTheException(e);
        var ApplicationError = new ApplicationError(BAD_REQUEST_ERROR_TITLE, "Неверное тело запроса");
        return new ResponseEntity<>(ApplicationError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({RuntimeException.class, Exception.class})
    public ResponseEntity<ApplicationError> handleException(Exception e) {
        logTheException(e);
        var ApplicationError = new ApplicationError(INTERNAL_SERVER_ERROR_ERROR_TITLE, "Внутренняя ошибка сервера");
        return new ResponseEntity<>(ApplicationError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApplicationError> handleNoResourceFoundException(Exception e) {
        logTheException(e);
        var ApplicationError = new ApplicationError(NOT_FOUND_ERROR_TITLE, "Ресурс не найден");
        return new ResponseEntity<>(ApplicationError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApplicationError> handleNoResourceFoundException(EntityNotFoundException e) {
        logTheException(e);
        var ApplicationError = new ApplicationError(NOT_FOUND_ERROR_TITLE, e.getMessage());
        return new ResponseEntity<>(ApplicationError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({AuthenticationException.class, InvalidBearerTokenException.class})
    public ResponseEntity<ApplicationError> handleAuthenticationException(Exception e) {
        logTheException(e);
        var ApplicationError = new ApplicationError("Ошибка аутентификации", e.getMessage());
        return new ResponseEntity<>(ApplicationError, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({AccessDeniedException.class, AuthorizationDeniedException.class})
    public ResponseEntity<ApplicationError> handleAccessDeniedException(Exception e) {
        logTheException(e);
        var ApplicationError = new ApplicationError("Недостаточно прав", e.getMessage());
        return new ResponseEntity<>(ApplicationError, HttpStatus.FORBIDDEN);
    }
}
