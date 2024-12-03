package com.challenge.checkout.gateway;

import com.challenge.checkout.entity.product.ProductWithPromotions;
import com.challenge.checkout.entity.product.ProductBase;

import java.util.Map;

public interface ProductGatewayMapper {
    public ProductBase mapProductBase(Map<String, Object> json);
    public ProductWithPromotions mapProductWithPromotions(Map<String, Object> json);
}