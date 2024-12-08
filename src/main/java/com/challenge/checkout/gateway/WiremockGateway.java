package com.challenge.checkout.gateway;

import com.challenge.checkout.entity.product.ProductBase;
import com.challenge.checkout.entity.product.ProductWithPromotions;

import java.util.List;

public interface WiremockGateway {
    List<ProductBase> getProducts(String baseURL);
    ProductWithPromotions getProductWithPromotions(String baseURL, String productId);
    MappingFormatEnum getMappingFormat();
}