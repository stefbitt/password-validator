package com.itau.password_validator.unit.validator;

import com.itau.password_validator.validator.AtLeastOneLowerCaseRule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AtLeastOneLowerCaseRuleTest {

    @InjectMocks
    private AtLeastOneLowerCaseRule rule;


    @Test
    void shouldReturnTrue_whenPasswordContainsAtLeastOneLowerCaseLetter() {

        String password = "ABCdef123";
        boolean result = rule.validate(password);
        assertTrue(result);
    }

    @Test
    void shouldReturnFalse_whenPasswordDoesNotContainLowerCaseLetters() {

        String password = "ABC123!@#";
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
        assertEquals("Password must contain at least one lowercase letter", errorMessage);
    }
}
