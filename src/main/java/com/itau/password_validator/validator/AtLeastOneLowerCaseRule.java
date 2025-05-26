package com.itau.password_validator.validator;

import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
public class AtLeastOneLowerCaseRule implements PasswordRule {

    @Override
    public boolean validate(String password) {
        return Objects.nonNull(password) && password.chars().anyMatch(Character::isLowerCase);
    }

    @Override
    public String getErrorMessage() {
        return "Password must contain at least one lowercase letter";
    }
}
