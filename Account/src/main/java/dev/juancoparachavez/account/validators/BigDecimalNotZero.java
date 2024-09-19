package dev.juancoparachavez.account.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BigDecimalNotZeroValidator.class)
public @interface BigDecimalNotZero {

    String message() default "El valor no puede ser cero.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
