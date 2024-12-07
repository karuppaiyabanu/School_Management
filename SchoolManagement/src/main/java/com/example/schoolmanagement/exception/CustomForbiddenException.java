package com.example.schoolmanagement.exception;

public class CustomForbiddenException extends RuntimeException {

    public CustomForbiddenException(final String message) {
        super(message);
        System.out.println("its working correct also");
    }

    public CustomForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }
}
