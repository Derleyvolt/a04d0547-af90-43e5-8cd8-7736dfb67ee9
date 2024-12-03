package com.test.checkout.service;

import com.test.checkout.entity.product.ProductBase;
import com.test.checkout.entity.product.ProductWithPromotions;
import com.test.checkout.gateway.GatewayMarketStrategy;
import com.test.checkout.gateway.WiremockGateway;
import com.test.checkout.model.TenantModel;
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
