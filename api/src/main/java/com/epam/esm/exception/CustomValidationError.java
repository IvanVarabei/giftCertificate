package com.epam.esm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.BindingResult;

@AllArgsConstructor
@Getter
public class CustomValidationError extends RuntimeException {
    private final transient BindingResult bindingResult;
}
