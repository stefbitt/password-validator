package com.itau.password_validator.unit.validator;

import com.itau.password_validator.validator.MinimumLengthRule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MinimumLengthRuleTest {

    @InjectMocks
    private MinimumLengthRule rule;


    @Test
    void shouldReturnTrue_whenPasswordHasAtLeastNineCharacters() {

        String password = "abc123DEF";
        boolean result = rule.validate(password);
        assertTrue(result);
    }

    @Test
    void shouldReturnFalse_whenPasswordHasLessThanNineCharacters() {

        String password = "abc123D";
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
        assertEquals("Password must have at least 9 characters", errorMessage);
    }
}
