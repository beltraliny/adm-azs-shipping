package com.github.beltraliny.admazsshipping.exceptions;

public class AddressValidationException extends RuntimeException {

    public AddressValidationException() {
        super("An unknown error occurred during the validation of address fields.");
    }

    public AddressValidationException(String message) {
        super(message);
    }
}
