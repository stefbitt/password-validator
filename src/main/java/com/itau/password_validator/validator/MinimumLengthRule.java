package com.itau.password_validator.validator;

import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
public class MinimumLengthRule implements PasswordRule {

    private static final int MIN_LENGTH = 9;

    @Override
    public boolean validate(String password) {
        return Objects.nonNull(password) && password.length() >= MIN_LENGTH;
    }

    @Override
    public String getErrorMessage() {
        return "Password must have at least 9 characters";
    }
}
