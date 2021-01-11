package com.epam.esm.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@EnableWebMvc
public class ErrorControllerAdvice {
    @ExceptionHandler(ResourceNotFoundException.class)
    ResponseEntity<ExceptionDto> handleException(ResourceNotFoundException ex) {
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setErrorMessage(ex.getMessage());
        exceptionDto.setErrorCode(40401);
        exceptionDto.setTimestamp(LocalDateTime.now());
        return ResponseEntity.status(NOT_FOUND).body(exceptionDto);
    }

    @ExceptionHandler(RepositoryException.class)
    ResponseEntity<ExceptionDto> handleException(RepositoryException ex) {
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setErrorMessage(ex.getMessage());
        exceptionDto.setErrorCode(40001);
        exceptionDto.setTimestamp(LocalDateTime.now());
        return ResponseEntity.badRequest().body(exceptionDto);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    ResponseEntity<ExceptionDto> handleException(DuplicateKeyException ex) {
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setErrorMessage(ex.getLocalizedMessage());
        exceptionDto.setErrorCode(40901);
        exceptionDto.setTimestamp(LocalDateTime.now());
        return ResponseEntity.status(409).body(exceptionDto);
    }

    @ExceptionHandler(CustomValidationError.class)
    ResponseEntity<ExceptionDtoWrapper> handleValidationExceptions(CustomValidationError ex) {
        ExceptionDtoWrapper exceptionDtoWrapper = new ExceptionDtoWrapper();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String errorMessage = error.getDefaultMessage();
            String fieldName = ((FieldError) error).getField();
            ExceptionDto exceptionDto = new ExceptionDto();
            exceptionDto.setErrorMessage(String.format("%s - %s", fieldName, errorMessage));
            exceptionDto.setErrorCode(400);
            exceptionDto.setTimestamp(LocalDateTime.now());
            exceptionDtoWrapper.getErrors().add(exceptionDto);
        });
        return ResponseEntity.badRequest().body(exceptionDtoWrapper);
    }

    // @PathVariable validation
    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<ExceptionDto> handleException(ConstraintViolationException ex) {
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setErrorMessage(ex.getMessage());
        exceptionDto.setErrorCode(400);
        exceptionDto.setTimestamp(LocalDateTime.now());
        return ResponseEntity.badRequest().body(exceptionDto);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    ResponseEntity<ExceptionDto> handleNotFoundMapping(NoHandlerFoundException ex) {
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setErrorMessage(ex.getMessage());
        exceptionDto.setErrorCode(NOT_FOUND.value());
        exceptionDto.setTimestamp(LocalDateTime.now());
        return ResponseEntity.status(NOT_FOUND).body(exceptionDto);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionDto> handleMessageNotReadableException(HttpMessageNotReadableException ex) {
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setErrorMessage(ex.getMessage());
        exceptionDto.setErrorCode(400);
        exceptionDto.setTimestamp(LocalDateTime.now());
        return ResponseEntity.badRequest().body(exceptionDto);
    }

//    @ExceptionHandler(Throwable.class)
//    ResponseEntity<ExceptionDto> handleException(Throwable e) {
//        log.error(e.getMessage(), e);
//        ExceptionDto exceptionDto = new ExceptionDto();
//        exceptionDto.setErrorMessage(e.getMessage());
//        exceptionDto.setErrorCode(500);
//        exceptionDto.setTimestamp(LocalDateTime.now());
//        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(exceptionDto);
//    }
}
