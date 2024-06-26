package com.github.beltraliny.admazsshipping.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    private ResponseEntity<ApiError> addressExceptionHandler(ValidationException validationException) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, validationException.getMessage());
        return ResponseEntity.status(apiError.getHttpStatus()).body(apiError);
    }

    @ExceptionHandler(Exception.class)
    private ResponseEntity<ApiError> defaultHandler(Exception exception) {
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "An unknown error occurred.");
        return ResponseEntity.status(apiError.getHttpStatus()).body(apiError);
    }
}
