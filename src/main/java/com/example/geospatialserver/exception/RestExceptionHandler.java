package com.example.geospatialserver.exception;

import jakarta.persistence.NoResultException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.ArrayList;
import java.util.List;

import static com.example.geospatialserver.util.ExceptionStringUtil.BAD_REQUEST_ERROR_TITLE;
import static com.example.geospatialserver.util.ExceptionStringUtil.INTERNAL_SERVER_ERROR_ERROR_TITLE;
import static com.example.geospatialserver.util.ExceptionStringUtil.NOT_FOUND_ERROR_TITLE;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApplicationError> handleValidationExceptions(MethodArgumentNotValidException e) {
        List<String> errors = new ArrayList<>();
        e.getBindingResult().getAllErrors()
                .forEach((error) -> errors.add(error.getDefaultMessage()));
        var applicationError = new ApplicationError(BAD_REQUEST_ERROR_TITLE, errors);
        return new ResponseEntity<>(applicationError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApplicationError> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        var ApplicationError = new ApplicationError(BAD_REQUEST_ERROR_TITLE, "Неверное тело запроса");
        return new ResponseEntity<>(ApplicationError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({RuntimeException.class, Exception.class})
    public ResponseEntity<ApplicationError> handleException(Exception e) {
        var ApplicationError = new ApplicationError(INTERNAL_SERVER_ERROR_ERROR_TITLE, "Внутренняя ошибка сервера");
        return new ResponseEntity<>(ApplicationError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApplicationError> handleNoResourceFoundException(Exception e) {
        var ApplicationError = new ApplicationError(NOT_FOUND_ERROR_TITLE, "Ресурс не найден");
        return new ResponseEntity<>(ApplicationError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<ApplicationError> handleNoResourceFoundException(NoResultException e) {
        var ApplicationError = new ApplicationError(NOT_FOUND_ERROR_TITLE, e.getMessage());
        return new ResponseEntity<>(ApplicationError, HttpStatus.NOT_FOUND);
    }
}
