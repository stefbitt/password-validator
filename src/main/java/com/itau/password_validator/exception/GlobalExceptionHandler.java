package com.itau.password_validator.exception;

import com.itau.password_validator.model.response.PasswordValidationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(PasswordValidationException.class)
    public ResponseEntity<PasswordValidationResponse> handlePasswordValidationException(PasswordValidationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(buildResponse(ex.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<PasswordValidationResponse> handleRuntimeException(RuntimeException ex) {
        log.error("Internal server error: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(buildResponse("An unexpected error occurred. Please try again later."));
    }

    private PasswordValidationResponse buildResponse(String exceptionMessage) {
        return PasswordValidationResponse.builder()
                .isValid(false)
                .errors(Collections.singletonList(exceptionMessage))
                .build();
    }

}