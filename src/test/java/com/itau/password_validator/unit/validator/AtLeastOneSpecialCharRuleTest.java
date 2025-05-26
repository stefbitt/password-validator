package com.itau.password_validator.unit.validator;

import com.itau.password_validator.validator.AtLeastOneSpecialCharRule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AtLeastOneSpecialCharRuleTest {

    @InjectMocks
    private AtLeastOneSpecialCharRule rule;

    @Test
    void shouldReturnTrue_whenPasswordContainsSpecialCharacter() {

        String password = "Abcdef1!";
        boolean result = rule.validate(password);
        assertTrue(result);
    }

    @Test
    void shouldReturnFalse_whenPasswordHasNoSpecialCharacter() {

        String password = "Abcdef123";
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
        assertEquals("Password must contain at least one special character", errorMessage);
    }
}
