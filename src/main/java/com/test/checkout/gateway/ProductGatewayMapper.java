package com.test.checkout.gateway;

import com.test.checkout.entity.product.ProductWithPromotions;
import com.test.checkout.entity.product.ProductBase;

import java.util.Map;

public interface ProductGatewayMapper {
    public ProductBase mapProductBase(Map<String, Object> json);
    public ProductWithPromotions mapProductWithPromotions(Map<String, Object> json);
}