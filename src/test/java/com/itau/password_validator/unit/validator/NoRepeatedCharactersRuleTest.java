package com.itau.password_validator.unit.validator;

import com.itau.password_validator.validator.NoRepeatedCharactersRule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class NoRepeatedCharactersRuleTest {

    @InjectMocks
    private NoRepeatedCharactersRule rule;



    @Test
    void shouldReturnTrue_whenPasswordHasNoRepeatedCharacters() {

        String password = "Abc123!@#";
        boolean result = rule.validate(password);
        assertTrue(result);
    }

    @Test
    void shouldReturnFalse_whenPasswordHasRepeatedCharacters() {

        String password = "Abc123A";
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
        assertEquals("Password must not contain repeated characters", message);
    }
}
