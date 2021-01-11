package com.epam.esm.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ContactNumberValidator implements ConstraintValidator<CustomEitherNullOrLengthGraterThan1, String> {

    @Override
    public void initialize(CustomEitherNullOrLengthGraterThan1 contactNumber) {
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext cxt) {
        return name == null || name.length() >= 2;
    }
}