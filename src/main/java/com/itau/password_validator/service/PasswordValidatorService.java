package com.itau.password_validator.service;

import com.itau.password_validator.exception.PasswordValidationException;
import com.itau.password_validator.validator.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PasswordValidatorService {

    private final List<PasswordRule> rules;

    public Pair<Boolean, List<String>> validateWithReason(String password) {

        if (Objects.isNull(password)) {
            throw new PasswordValidationException("Password cannot be null");
        }

        List<String> errors = getValidationErrors(password);

        return Pair.of(errors.isEmpty(), errors);
    }

    private List<String> getValidationErrors(String password) {
        return rules.stream()
                .filter(rule -> !rule.validate(password))
                .map(PasswordRule::getErrorMessage)
                .toList();
    }
}
