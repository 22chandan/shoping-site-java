package com.webshopify.plateform.features.customers.customvalidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class WeshopifyPasswordConstraint implements ConstraintValidator<PasswordValidator, String> {

    @Override
    public void initialize(PasswordValidator constraintAnnotation) {
        // No initialization required for now
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            return false;
        }

        // Password must contain at least one digit, one letter, 6–20 characters
        return password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,20}$");
    }
}
