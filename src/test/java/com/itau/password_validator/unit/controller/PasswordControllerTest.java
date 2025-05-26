package com.itau.password_validator.unit.controller;

import com.itau.password_validator.controller.PasswordController;
import com.itau.password_validator.model.request.PasswordRequest;
import com.itau.password_validator.model.response.PasswordValidationResponse;
import com.itau.password_validator.service.PasswordValidatorService;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PasswordControllerTest {

    @Mock
    private PasswordValidatorService validatorService;

    @InjectMocks
    private PasswordController passwordController;

    @Test
    void shouldReturnValidResponse_whenPasswordIsValid() {
        String validPassword = "StrongPassword123!";

        PasswordRequest request = buildPasswordRequest(validPassword);

        Pair<Boolean, List<String>> validationResult = Pair.of(true, List.of());

        when(validatorService.validateWithReason(validPassword))
                .thenReturn(validationResult);

        ResponseEntity<PasswordValidationResponse> response = passwordController.validatePassword(request);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(validationResult.getKey(), response.getBody().isValid());
        Assertions.assertTrue(response.getBody().getErrors().isEmpty());
    }

    @Test
    void shouldReturnInvalidResponse_whenPasswordIsInvalid() {

        String invalidPassword = "abc";
        PasswordRequest request = buildPasswordRequest(invalidPassword);

        List<String> errors = List.of(
                "Password must contain at least one digit",
                "Password must contain at least one special character",
                "Password must contain at least one uppercase letter",
                "Password must have at least 9 characters",
                "Password must not contain repeated characters"
        );

        Pair<Boolean, List<String>> validationResult = Pair.of(false, errors);

        when(validatorService.validateWithReason(invalidPassword)).thenReturn(validationResult);

        ResponseEntity<PasswordValidationResponse> response = passwordController.validatePassword(request);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(validationResult.getKey(), response.getBody().isValid());
        Assertions.assertFalse(response.getBody().getErrors().isEmpty());
    }

    private PasswordRequest buildPasswordRequest(String password) {
        return PasswordRequest.builder()
                .password(password)
                .build();
    }
}