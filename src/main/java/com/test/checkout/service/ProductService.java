package com.test.checkout.service;

import com.test.checkout.datasource.TenantDatasourceContextHolder;
import com.test.checkout.entity.product.ProductBase;
import com.test.checkout.entity.product.ProductWithPromotions;
import com.test.checkout.gateway.GatewayMarketStrategy;
import com.test.checkout.gateway.WiremockGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.checkout.entity.promotion.Promotion;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    GatewayMarketStrategy gatewayMarketStrategy;

    public ProductWithPromotions getProductWithPromotions(String productId) {
        WiremockGateway wiremockGateway = gatewayMarketStrategy.getMarketGateway(
                TenantDatasourceContextHolder.getTenantDatasource()
        );

        return wiremockGateway.getProductWithPromotions(productId);
    }

    public List<ProductBase> getProducts() {
        WiremockGateway wiremockGateway = gatewayMarketStrategy.getMarketGateway(
                TenantDatasourceContextHolder.getTenantDatasource()
        );

        return wiremockGateway.getProducts();
    }
}
