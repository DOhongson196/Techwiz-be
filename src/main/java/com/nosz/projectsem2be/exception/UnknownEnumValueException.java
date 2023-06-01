package com.nosz.projectsem2be.exception;


public class UnknownEnumValueException extends RuntimeException {
    public UnknownEnumValueException(String message) {
        super(message);
    }
}
