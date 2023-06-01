package com.nosz.projectsem2be.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(CategoryException.class)
    public final ResponseEntity<Object> handlerCategoryException(CategoryException ex){
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage());

        return  new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FileNotFoundException.class)
    public final ResponseEntity<Object> handlerFileNotFoundException(FileNotFoundException ex){
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage());

        return  new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FileStorageException.class)
    public final ResponseEntity<Object> handlerFileStorageException(FileStorageException ex){
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage());

        return  new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ManufacturerException.class)
    public final ResponseEntity<Object> handlerManufacturerException(ManufacturerException ex){
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage());

        return  new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductException.class)
    public final ResponseEntity<Object> handlerProductException(ProductException ex){
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage());

        return  new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvoiceException.class)
    public final ResponseEntity<Object> handlerInvoiceException(InvoiceException ex){
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage());

        return  new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvoiceDetailException.class)
    public final ResponseEntity<Object> handlerInvoiceDetailException(InvoiceDetailException ex){
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage());

        return  new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserException.class)
    public final ResponseEntity<Object> handlerUserException(UserException ex){
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage());

        return  new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserLoginException.class)
    public final ResponseEntity<Object> handlerUserLoginException(UserLoginException ex){
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage());

        return  new ResponseEntity<>(exceptionResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ConfirmTokenException.class)
    public final ResponseEntity<Object> handlerConfirmTokenException(ConfirmTokenException ex){
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage());

        return  new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CartException.class)
    public final ResponseEntity<Object> handlerCartException(CartException ex){
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage());

        return  new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnknownEnumValueException.class)
    public final ResponseEntity<Object> handlerUnknownEnumValueException(UnknownEnumValueException ex){
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage());

        return  new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TokenExpiredException.class)
    public final ResponseEntity<Object> handlerTokenExpiredException(TokenExpiredException ex){
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage());

        return  new ResponseEntity<>(exceptionResponse, HttpStatus.FORBIDDEN);
    }

}
