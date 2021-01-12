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
        if (value == null || value.matches(regex)) {
            return true;
        }
        cxt.disableDefaultConstraintViolation();
        cxt.buildConstraintViolationWithTemplate(String.format("Must be either null or match the pattern : %s", regex))
                .addConstraintViolation();
        return false;
    }
}