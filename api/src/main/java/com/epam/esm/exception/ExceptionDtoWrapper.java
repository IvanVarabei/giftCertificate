package com.epam.esm.exception;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ExceptionDtoWrapper {
    private List<ExceptionDto> errors = new ArrayList<>();
}
