package com.test.checkout.exception;

import org.springframework.http.HttpStatus;

public class ConflictException extends RuntimeException {
    public ConflictException(String detail) {
        super(detail);
    }

    public HttpStatus getStatusCode() {
        return HttpStatus.CONFLICT;
    }
}
