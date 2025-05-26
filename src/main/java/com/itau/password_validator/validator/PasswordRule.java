package com.itau.password_validator.validator;

public interface PasswordRule {

    boolean validate(String password);

    String getErrorMessage();
}