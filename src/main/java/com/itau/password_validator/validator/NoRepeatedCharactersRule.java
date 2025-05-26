package com.itau.password_validator.validator;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Component
public class NoRepeatedCharactersRule implements PasswordRule {

    @Override
    public boolean validate(String password) {

        if (Objects.isNull(password)) return false;

        Set<Character> seen = new HashSet<>();
        for (char c : password.toCharArray()) {
            if (!seen.add(c)) return false;
        }

        return true;
    }

    @Override
    public String getErrorMessage() {
        return "Password must not contain repeated characters";
    }
}
