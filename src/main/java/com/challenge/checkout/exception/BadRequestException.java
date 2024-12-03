package com.challenge.checkout.exception;

import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

public class BadRequestException extends RuntimeException {
    private Map<String, List<String>> fieldErrors;
    private List<String> listErrors;

    public BadRequestException() {
        super();
    }

    public BadRequestException(String detail) {
        super(detail);
    }

    public BadRequestException(Map<String, List<String>> errors, String detail) {
        super(detail);

        this.fieldErrors = errors;
    }

    public BadRequestException(List<String> errors, String detail) {
        super(detail);

        this.listErrors = errors;
    }

    public Object getErrors() {
        if(fieldErrors != null) {
            return fieldErrors;
        }

        return listErrors;
    }

    public HttpStatus getStatusCode() {
        return HttpStatus.BAD_REQUEST;
    }
}

