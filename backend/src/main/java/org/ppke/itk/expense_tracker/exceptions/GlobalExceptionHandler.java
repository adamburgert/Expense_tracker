package org.ppke.itk.expense_tracker.exceptions;

import org.ppke.itk.expense_tracker.domain.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.NoSuchElementException;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public ErrorResponse handleNoSuchElementException(NoSuchElementException ex) {
        return new ErrorResponse("Resource not found: " + ex.getMessage(), HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResponse handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ErrorResponse("Invalid input: " + ex.getMessage(), HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ErrorResponse handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String paramName = ex.getName();
        String message = "Invalid type for parameter '" + paramName + "'. Expected a " + Objects.requireNonNull(ex.getRequiredType()).getSimpleName();

        return new ErrorResponse(message, HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(Exception.class)
    public ErrorResponse handleGenericException(Exception ex) {

        return new ErrorResponse("An unexpected error occurred: " + ex.getMessage(), HttpStatus.NOT_FOUND.value());
    }
}
