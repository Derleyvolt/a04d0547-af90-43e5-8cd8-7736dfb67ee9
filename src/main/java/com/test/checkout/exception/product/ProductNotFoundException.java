package com.test.checkout.exception.product;

import com.test.checkout.exception.NotFoundException;

public class ProductNotFoundException extends NotFoundException {
    public ProductNotFoundException(String detail) {
        super(detail);
    }
}
