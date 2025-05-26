package com.itau.password_validator.unit.validator;

import com.itau.password_validator.validator.AtLeastOneDigitRule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AtLeastOneDigitRuleTest {
    @InjectMocks
    private AtLeastOneDigitRule rule;

    @Test
    void shouldReturnTrue_whenPasswordContainsAtLeastOneDigit() {

        String password = "abc123";
        boolean result = rule.validate(password);
        assertTrue(result);
    }

    @Test
    void shouldReturnFalse_whenPasswordDoesNotContainDigits() {

        String password = "abcdef";
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

        String message = rule.getErrorMessage();
        assertEquals("Password must contain at least one digit", message);
    }
}
