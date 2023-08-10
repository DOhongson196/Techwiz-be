package com.nosz.techwiz.exception;


public class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }

    public CustomException(String message,Exception ex) {
        super(message);
    }
}
