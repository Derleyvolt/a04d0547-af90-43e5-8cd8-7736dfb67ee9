package com.challenge.checkout.exception.basket;

import com.challenge.checkout.exception.ForbiddenException;

public class BasketForbiddenException extends ForbiddenException {
    public BasketForbiddenException(String detail) {
        super(detail);
    }
}