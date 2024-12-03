package com.challenge.checkout.exception.basket;

import com.challenge.checkout.exception.ConflictException;

public class BasketAlreadyOpenException extends ConflictException {
    public BasketAlreadyOpenException() {
        super("Basket already open");
    }
}
