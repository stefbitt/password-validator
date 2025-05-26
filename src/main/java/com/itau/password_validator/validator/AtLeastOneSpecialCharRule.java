package com.itau.password_validator.validator;

import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;


@Component
public class AtLeastOneSpecialCharRule implements PasswordRule {

    private static final Set<Character> SPECIAL_CHARS = Set.of('!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '+');

    @Override
    public boolean validate(String password) {
        return Objects.nonNull(password) && password.chars().anyMatch(c -> SPECIAL_CHARS.contains((char) c));
    }

    @Override
    public String getErrorMessage() {
        return "Password must contain at least one special character";
    }
}
