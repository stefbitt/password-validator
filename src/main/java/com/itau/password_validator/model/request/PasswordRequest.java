package com.itau.password_validator.model.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PasswordRequest {
    private String password;
}
