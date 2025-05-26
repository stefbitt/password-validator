package com.itau.password_validator.validator;

import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
public class AtLeastOneDigitRule implements PasswordRule {

    @Override
    public boolean validate(String password) {
        return Objects.nonNull(password) && password.chars().anyMatch(Character::isDigit);
    }

    @Override
    public String getErrorMessage() {
        return "Password must contain at least one digit";
    }
}