package com.github.beltraliny.admazsshipping.exceptions;

public class ValidationException extends RuntimeException {

    public ValidationException() {
        super("An unknown error occurred during the validation of fields.");
    }

    public ValidationException(String message) {
        super(message);
    }
}
