package com.challenge.checkout.exception.basket;

import com.challenge.checkout.exception.NotFoundException;

public class BasketNotFoundException extends NotFoundException {
    public BasketNotFoundException() {
        super("Basket Not found");
    }
}