package com.epam.esm.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice("com.epam.api")
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ErrorControllerAdvice {
    @ExceptionHandler(ResourceNotFoundException.class)
    ResponseEntity handleException(ResourceNotFoundException ex) {
        String localizedMessage = ex.getLocalizedMessage();
//        //custom class 3 fealds :  status, message
//        public final String field;
//        public final String code;
//        public final String message;
        return ResponseEntity.notFound().build();
    }


}
