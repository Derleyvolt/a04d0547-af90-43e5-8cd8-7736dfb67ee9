package com.test.checkout.gateway;

import com.test.checkout.entity.product.ProductBase;
import com.test.checkout.entity.product.ProductWithPromotions;

import java.util.List;

public interface WiremockGateway {
    public List<ProductBase> getProducts(String baseURL);
    public ProductWithPromotions getProductWithPromotions(String baseURL, String productId);
    public String getTenantName();
}