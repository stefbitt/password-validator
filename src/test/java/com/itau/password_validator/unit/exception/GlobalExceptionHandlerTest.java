package com.itau.password_validator.unit.exception;


import com.itau.password_validator.exception.GlobalExceptionHandler;
import com.itau.password_validator.exception.PasswordValidationException;
import com.itau.password_validator.model.response.PasswordValidationResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler handler;

    private static final String UNEXPECTED_ERROR_MESSAGE = "An unexpected error occurred. Please try again later.";

    private static final String PASSWORD_VALIDATION_ERROR_MESSAGE = "Password must contain a digit";

    private static final int ERROR_LIST_SIZE = 1;

    @Test
    void shouldHandlePasswordValidationException() {

        PasswordValidationException exception = new PasswordValidationException(PASSWORD_VALIDATION_ERROR_MESSAGE);

        ResponseEntity<PasswordValidationResponse> response = handler.handlePasswordValidationException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isValid());
        assertEquals(ERROR_LIST_SIZE, response.getBody().getErrors().size());
        assertEquals(PASSWORD_VALIDATION_ERROR_MESSAGE, response.getBody().getErrors().getFirst());
    }

    @Test
    void shouldHandleRuntimeException() {

        RuntimeException exception = new RuntimeException("Unexpected failure");

        ResponseEntity<PasswordValidationResponse> response = handler.handleRuntimeException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isValid());
        assertEquals(ERROR_LIST_SIZE, response.getBody().getErrors().size());
        assertEquals(UNEXPECTED_ERROR_MESSAGE, response.getBody().getErrors().getFirst());
    }
}