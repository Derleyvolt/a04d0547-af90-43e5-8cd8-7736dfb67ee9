package com.test.checkout.exception.basket;

import com.test.checkout.exception.ConflictException;

public class BasketAlreadyOpenException extends ConflictException {
    public BasketAlreadyOpenException() {
        super("Basket already open");
    }
}
