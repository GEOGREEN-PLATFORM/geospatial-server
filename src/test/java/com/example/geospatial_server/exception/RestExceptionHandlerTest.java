package com.example.geospatial_server.exception;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.lang.reflect.Method;

import static com.example.geospatial_server.util.ExceptionStringUtil.BAD_REQUEST_ERROR_TITLE;
import static com.example.geospatial_server.util.ExceptionStringUtil.INTERNAL_SERVER_ERROR_ERROR_TITLE;
import static com.example.geospatial_server.util.ExceptionStringUtil.NOT_FOUND_ERROR_TITLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

class RestExceptionHandlerTest {
    private RestExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new RestExceptionHandler();
    }

    @Test
    void handleValidationExceptions_ReturnsBadRequest_WithFieldErrors() throws Exception {
        Method method = this.getClass().getMethod("dummyMethod", String.class);
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "obj");
        bindingResult.addError(new FieldError("obj", "field1", "must not be blank"));
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(
                new org.springframework.core.MethodParameter(method, 0), bindingResult);

        ResponseEntity<ApplicationError> response = handler.handleValidationExceptions(ex);

        assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST);
        ApplicationError error = response.getBody();
        assert error != null;
        assertThat(error.getTitle()).isEqualTo(BAD_REQUEST_ERROR_TITLE);
        assertThat(error.getMessages()).containsExactly("field1 : must not be blank");
    }

    public void dummyMethod(String s) { /* for MethodParameter */ }

    @Test
    void handleIllegalArgumentExceptionException_ReturnsBadRequest_WithMessage() {
        IllegalArgumentException ex = new IllegalArgumentException("illegal arg");

        ResponseEntity<ApplicationError> response = handler.handleIllegalArgumentExceptionException(ex);

        assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST);
        ApplicationError error = response.getBody();
        assert error != null;
        assertThat(error.getTitle()).isEqualTo(BAD_REQUEST_ERROR_TITLE);
        assertThat(error.getMessage()).isEqualTo("illegal arg");
    }

    @Test
    void handleHttpMessageNotReadableException_ReturnsBadRequest_WithGenericMessage() {
        HttpMessageNotReadableException ex = new HttpMessageNotReadableException("bad JSON");

        ResponseEntity<ApplicationError> response = handler.handleHttpMessageNotReadableException(ex);

        assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST);
        ApplicationError error = response.getBody();
        assert error != null;
        assertThat(error.getTitle()).isEqualTo(BAD_REQUEST_ERROR_TITLE);
        assertThat(error.getMessage()).isEqualTo("Неверное тело запроса");
    }

    @Test
    void handleException_ReturnsInternalServerError_WithGenericMessage() {
        Exception ex = new Exception("oops");

        ResponseEntity<ApplicationError> response = handler.handleException(ex);

        assertThat(response.getStatusCode()).isEqualTo(INTERNAL_SERVER_ERROR);
        ApplicationError error = response.getBody();
        assert error != null;
        assertThat(error.getTitle()).isEqualTo(INTERNAL_SERVER_ERROR_ERROR_TITLE);
        assertThat(error.getMessage()).isEqualTo("Внутренняя ошибка сервера");
    }


    @Test
    void handleEntityNotFoundException_ReturnsNotFound_WithExceptionMessage() {
        EntityNotFoundException ex = new EntityNotFoundException("not found");

        ResponseEntity<ApplicationError> response = handler.handleNoResourceFoundException(ex);

        assertThat(response.getStatusCode()).isEqualTo(NOT_FOUND);
        ApplicationError error = response.getBody();
        assert error != null;
        assertThat(error.getTitle()).isEqualTo(NOT_FOUND_ERROR_TITLE);
        assertThat(error.getMessage()).isEqualTo("not found");
    }
}