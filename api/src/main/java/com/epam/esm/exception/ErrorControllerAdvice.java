package com.epam.esm.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ErrorControllerAdvice {
    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    ResponseEntity<ExceptionDto> handleException(ResourceNotFoundException e) {
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setErrorMessage(e.getMessage());
        exceptionDto.setErrorCode(40401);
        exceptionDto.setTimestamp(LocalDateTime.now());
        return ResponseEntity.badRequest().body(exceptionDto);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(RepositoryException.class)
    ResponseEntity<ExceptionDto> handleException(RepositoryException e) {
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setErrorMessage(e.getMessage());
        exceptionDto.setErrorCode(40001);
        exceptionDto.setTimestamp(LocalDateTime.now());
        return ResponseEntity.badRequest().body(exceptionDto);
    }

    @ResponseStatus(CONFLICT)
    @ExceptionHandler(DuplicateKeyException.class)
    ResponseEntity<ExceptionDto> handleException(DuplicateKeyException e) {
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setErrorMessage(e.getMessage());
        exceptionDto.setErrorCode(40901);
        exceptionDto.setTimestamp(LocalDateTime.now());
        return ResponseEntity.badRequest().body(exceptionDto);
    }
}
