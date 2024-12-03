package com.test.checkout.exception.basket;

import com.test.checkout.exception.NotFoundException;

public class BasketNotFoundException extends NotFoundException {
    public BasketNotFoundException() {
        super("Basket Not found");
    }
}