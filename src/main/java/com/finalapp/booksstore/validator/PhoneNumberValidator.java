package com.finalapp.booksstore.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {
    @Override
    public void initialize(PhoneNumber constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        String validPattern1 = "\\d{9}";
        String validPattern2 = "\\+40\\d{9}";

        return value.matches(validPattern1) || value.matches(validPattern2);
    }
}
