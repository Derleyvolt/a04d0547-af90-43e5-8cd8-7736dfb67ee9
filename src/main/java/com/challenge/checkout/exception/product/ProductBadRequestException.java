package com.challenge.checkout.exception.product;

import com.challenge.checkout.exception.BadRequestException;

import java.util.List;
import java.util.Map;

public class ProductBadRequestException extends BadRequestException {
    public ProductBadRequestException(Map<String, List<String>> fieldErrors) {
        super(fieldErrors, "Products not found");
    }

    public ProductBadRequestException(List<String> errors) {
        super(errors, "Products do not exists");
    }
}