package dev.juancoparachavez.account.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;

public class BigDecimalNotZeroValidator implements ConstraintValidator<BigDecimalNotZero, BigDecimal> {
    @Override
    public boolean isValid(BigDecimal value, ConstraintValidatorContext context) {
        return value != null && value.compareTo(BigDecimal.ZERO) != 0;
    }
}
