package com.test.checkout.exception.basket;

import com.test.checkout.exception.ForbiddenException;

public class BasketForbiddenException extends ForbiddenException {
    public BasketForbiddenException(String detail) {
        super(detail);
    }
}