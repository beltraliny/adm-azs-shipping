package com.github.beltraliny.admazsshipping.exceptions;

public class CustomerValidationException extends RuntimeException {

    public CustomerValidationException() {
        super("An unknown error occurred during the validation of customer fields.");
    }

    public CustomerValidationException(String message) {
        super(message);
    }
}
