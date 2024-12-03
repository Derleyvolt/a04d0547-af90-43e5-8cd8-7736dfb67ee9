package com.challenge.checkout.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String detail) {
        super(detail);
    }

    public HttpStatus getStatusCode() {
        return HttpStatus.FORBIDDEN;
    }
}
