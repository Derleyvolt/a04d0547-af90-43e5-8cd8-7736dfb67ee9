package com.challenge.checkout.service;

import com.challenge.checkout.entity.product.ProductBase;
import com.challenge.checkout.entity.product.ProductWithPromotions;
import com.challenge.checkout.gateway.GatewayMarketStrategy;
import com.challenge.checkout.gateway.WiremockGateway;
import com.challenge.checkout.model.TenantModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    GatewayMarketStrategy gatewayMarketStrategy;

    public ProductWithPromotions getProductWithPromotions(TenantModel tenantModel, String productId) {
        WiremockGateway wiremockGateway = gatewayMarketStrategy.getMarketGateway(tenantModel.getName());
        return wiremockGateway.getProductWithPromotions(tenantModel.getURL(), productId);
    }

    public List<ProductBase> getProducts(TenantModel tenantModel) {
        WiremockGateway wiremockGateway = gatewayMarketStrategy.getMarketGateway(tenantModel.getName());
        return wiremockGateway.getProducts(tenantModel.getURL());
    }
}
