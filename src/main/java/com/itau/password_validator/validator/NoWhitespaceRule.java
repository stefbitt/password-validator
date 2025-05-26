package com.itau.password_validator.validator;

import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
public class NoWhitespaceRule implements PasswordRule {

    @Override
    public boolean validate(String password) {
        return Objects.nonNull(password) && !password.contains(" ");
    }

    @Override
    public String getErrorMessage() {
        return "Password must not contain spaces";
    }
}