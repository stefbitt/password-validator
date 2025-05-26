package com.itau.password_validator.unit.validator;

import com.itau.password_validator.validator.NoWhitespaceRule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class NoWhitespaceRuleTest {

    @InjectMocks
    private NoWhitespaceRule rule;

    @Test
    void shouldReturnTrue_whenPasswordDoesNotContainWhitespace() {

        String password = "Abc123!@#";
        boolean result = rule.validate(password);
        assertTrue(result);
    }

    @Test
    void shouldReturnFalse_whenPasswordContainsWhitespace() {

        String password = "Abc 123";
        boolean result = rule.validate(password);
        assertFalse(result);
    }

    @Test
    void shouldReturnFalse_whenPasswordIsNull() {

        String password = null;
        boolean result = rule.validate(password);
        assertFalse(result);
    }

    @Test
    void shouldReturnCorrectErrorMessage() {

        String errorMessage = rule.getErrorMessage();
        assertEquals("Password must not contain spaces", errorMessage);
    }
}
