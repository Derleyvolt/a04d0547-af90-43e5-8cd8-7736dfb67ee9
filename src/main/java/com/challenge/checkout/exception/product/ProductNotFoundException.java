package com.challenge.checkout.exception.product;

import com.challenge.checkout.exception.NotFoundException;

public class ProductNotFoundException extends NotFoundException {
    public ProductNotFoundException(String detail) {
        super(detail);
    }
}
