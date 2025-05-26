package com.itau.password_validator.unit.service;

import com.itau.password_validator.exception.PasswordValidationException;
import com.itau.password_validator.service.PasswordValidatorService;
import com.itau.password_validator.validator.PasswordRule;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PasswordValidatorServiceTest {

    @Mock
    private PasswordRule atLeastOneDigitRule;

    @Mock
    private PasswordRule atLeastOneSpecialCharRule;

    @Mock
    private PasswordRule atLeastOneLowerCaseRule;

    @Mock
    private PasswordRule minimumLengthRule;

    @Mock
    private PasswordRule noRepeatedCharactersRule;

    @Mock
    private PasswordRule noWhitespaceRule;


    @InjectMocks
    private PasswordValidatorService validatorService;

    @BeforeEach
    void setup() {
        validatorService = new PasswordValidatorService(List.of(atLeastOneDigitRule, atLeastOneSpecialCharRule,
                atLeastOneLowerCaseRule, minimumLengthRule, noRepeatedCharactersRule, noWhitespaceRule));
    }

    @Test
    void shouldReturnValidResult_whenAllRulesPass() {

        String password = "Strong123!";

        when(atLeastOneDigitRule.validate(password)).thenReturn(true);
        when(atLeastOneSpecialCharRule.validate(password)).thenReturn(true);
        when(atLeastOneLowerCaseRule.validate(password)).thenReturn(true);
        when(minimumLengthRule.validate(password)).thenReturn(true);
        when(noRepeatedCharactersRule.validate(password)).thenReturn(true);
        when(noWhitespaceRule.validate(password)).thenReturn(true);

        Pair<Boolean, List<String>> result = validatorService.validateWithReason(password);

        assertTrue(result.getLeft());
        assertTrue(result.getRight().isEmpty());
    }

    @Test
    void shouldReturnInvalidResult_whenSomeRulesFail() {

        String password = "weak";

        when(atLeastOneDigitRule.validate(password)).thenReturn(false);
        when(atLeastOneDigitRule.getErrorMessage()).thenReturn("Password must contain at least one digit");
        when(atLeastOneSpecialCharRule.validate(password)).thenReturn(true);

        Pair<Boolean, List<String>> result = validatorService.validateWithReason(password);

        assertFalse(result.getLeft());
        assertEquals(5, result.getRight().size());
        assertEquals("Password must contain at least one digit", result.getRight().getFirst());
    }

    @Test
    void shouldThrowException_whenPasswordIsNull() {

        String password = null;

        PasswordValidationException exception = assertThrows(
                PasswordValidationException.class,
                () -> validatorService.validateWithReason(password)
        );

        assertEquals("Password cannot be null", exception.getMessage());
    }
}
