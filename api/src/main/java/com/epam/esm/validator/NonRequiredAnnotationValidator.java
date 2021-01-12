package com.epam.esm.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NonRequiredAnnotationValidator implements ConstraintValidator<NonRequired, String> {
    private String regex;

    @Override
    public void initialize(NonRequired validation) {
        regex = validation.regex();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext cxt) {
        return value == null || value.matches(regex);
    }
}