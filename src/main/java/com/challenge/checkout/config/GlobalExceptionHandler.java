package com.challenge.checkout.config;

import com.challenge.checkout.exception.BadRequestException;
import com.challenge.checkout.exception.NotFoundException;
import com.challenge.checkout.exception.ServiceUnavailableException;
import com.challenge.checkout.exception.basket.BasketAlreadyOpenException;
import com.challenge.checkout.exception.basket.BasketForbiddenException;
import com.challenge.checkout.exception.basket.BasketNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.InvalidUrlException;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationExceptions(MethodArgumentNotValidException ex) {
        ProblemDetail problemDetails = ex.getBody();

        Map<String, String> errors = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        problemDetails.setProperty("errors", errors);
        return problemDetails;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail handleConstraintViolationException(ConstraintViolationException ex) {
        ProblemDetail problemDetails = ProblemDetail
                .forStatusAndDetail(HttpStatus.BAD_REQUEST, "Invalid request content.");

        Map<String, String> errors = new HashMap<>();

        ex.getConstraintViolations().forEach(violation -> {
            String fieldName = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            errors.put(fieldName, message);
        });

        problemDetails.setProperty("errors", errors);
        return problemDetails;
    }

    @ExceptionHandler(BadRequestException.class)
    public ProblemDetail handleBadRequestException(BadRequestException ex) {
        ProblemDetail problemDetails = ProblemDetail
                .forStatusAndDetail(ex.getStatusCode(), ex.getMessage());

        if (ex.getErrors() != null) {
            problemDetails.setProperty("errors", ex.getErrors());
        }

        problemDetails.setProperty("timestamp", Instant.now());

        return problemDetails;
    }

    @ExceptionHandler(BasketNotFoundException.class)
    public ProblemDetail handleBasketNotFoundException(BasketNotFoundException ex) {
        ProblemDetail problemDetails = ProblemDetail
                .forStatusAndDetail(ex.getStatusCode(), ex.getLocalizedMessage());

        problemDetails.setProperty("timestamp", Instant.now());
        return problemDetails;
    }

    @ExceptionHandler(NotFoundException.class)
    public ProblemDetail handleNotFoundException(NotFoundException ex) {
        ProblemDetail problemDetails = ProblemDetail
                .forStatusAndDetail(ex.getStatusCode(), ex.getMessage());

        problemDetails.setTitle("Not Found");
        problemDetails.setProperty("timestamp", Instant.now());

        return problemDetails;
    }

    @ExceptionHandler(BasketForbiddenException.class)
    public ProblemDetail handleBasketForbiddenException(BasketForbiddenException ex) {
        ProblemDetail problemDetails = ProblemDetail
                .forStatusAndDetail(ex.getStatusCode(), ex.getMessage());

        problemDetails.setTitle("Operation not Allowed");
        problemDetails.setProperty("timestamp", Instant.now());

        return problemDetails;
    }

    @ExceptionHandler(BasketAlreadyOpenException.class)
    public ProblemDetail handleBasketForbiddenException(BasketAlreadyOpenException ex) {
        ProblemDetail problemDetails = ProblemDetail
                .forStatusAndDetail(ex.getStatusCode(), ex.getMessage());

        problemDetails.setTitle("Basket State Conflict");
        problemDetails.setProperty("timestamp", Instant.now());

        return problemDetails;
    }

    @ExceptionHandler(ServiceUnavailableException.class)
    public ProblemDetail handleServiceUnavailableException(ServiceUnavailableException ex) {
        ProblemDetail problemDetails = ProblemDetail
                .forStatusAndDetail(ex.getStatusCode(), ex.getMessage());

        problemDetails.setTitle("Service Unavailable");
        problemDetails.setProperty("timestamp", Instant.now());

        return problemDetails;
    }

    @ExceptionHandler(InvalidUrlException.class)
    public ProblemDetail handleServiceUnavailableException(InvalidUrlException ex) {
        ProblemDetail problemDetails = ProblemDetail
                .forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());

        problemDetails.setTitle("Invalid URL");
        problemDetails.setProperty("timestamp", Instant.now());

        return problemDetails;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        ProblemDetail problemDetails = ProblemDetail
                .forStatusAndDetail(
                        HttpStatus.BAD_REQUEST,
                        "The operation violates a database constraint, such as a duplicate value or invalid reference.");

        problemDetails.setTitle("Data Integrity Violation");
        problemDetails.setProperty("timestamp", Instant.now());

        return problemDetails;
    }
}