package com.nosz.techwiz.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public final ResponseEntity<Object> handlerCartException(CustomException ex){
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage());

        return  new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<Object> handlerResourceNotFoundException(ResourceNotFoundException ex){
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage());
        return  new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }
}
