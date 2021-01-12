package com.epam.esm.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;

/**
 * The annotated element must be a string whose value must match to the specified regex or be null.
 * Supported type is: String
 */
@Documented
@Constraint(validatedBy = NonRequiredAnnotationValidator.class)
@Target({PARAMETER, TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NonRequired {
    String message() default "";

    String regex() default ".*";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}