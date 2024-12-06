package com.challenge.checkout.exception;

import org.springframework.http.HttpStatus;

public class ServiceUnavailableException extends RuntimeException {
    public ServiceUnavailableException() {
        super("Failed to connect to the Gateway API");
    }

    public HttpStatus getStatusCode() {
        return HttpStatus.SERVICE_UNAVAILABLE;
    }
}
