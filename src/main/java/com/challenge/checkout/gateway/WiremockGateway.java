package com.challenge.checkout.gateway;

import com.challenge.checkout.entity.product.ProductBase;
import com.challenge.checkout.entity.product.ProductWithPromotions;

import java.util.List;

public interface WiremockGateway {
    public List<ProductBase> getProducts(String baseURL);
    public ProductWithPromotions getProductWithPromotions(String baseURL, String productId);
    public String getTenantName();
}