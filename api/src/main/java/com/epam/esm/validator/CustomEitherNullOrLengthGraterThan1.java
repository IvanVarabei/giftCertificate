package com.epam.esm.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ContactNumberValidator.class)
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomEitherNullOrLengthGraterThan1 {
    String message() default "must be either null or longer than 1 symbol";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}