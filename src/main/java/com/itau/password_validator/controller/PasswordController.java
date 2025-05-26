package com.itau.password_validator.controller;

import com.itau.password_validator.model.request.PasswordRequest;
import com.itau.password_validator.model.response.PasswordValidationResponse;
import com.itau.password_validator.service.PasswordValidatorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/password")
public class PasswordController {

    private final PasswordValidatorService validatorService;

    @PostMapping("/validate")
    public ResponseEntity<PasswordValidationResponse> validatePassword(@RequestBody PasswordRequest request) {
        String password = request.getPassword();
        log.info("Received password validation request.");

        Pair<Boolean, List<String>> passwordValidated = validatorService.validateWithReason(password);

        log.info("Password validation result: {}", passwordValidated);

        return ResponseEntity.ok(buildResponse(passwordValidated));

    }

    private PasswordValidationResponse buildResponse(Pair<Boolean, List<String>> passwordValidated) {
        return PasswordValidationResponse.builder()
                .isValid(passwordValidated.getLeft())
                .errors(passwordValidated.getRight())
                .build();
    }
}
