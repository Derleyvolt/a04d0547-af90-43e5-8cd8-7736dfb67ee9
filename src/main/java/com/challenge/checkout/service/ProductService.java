package com.challenge.checkout.service;

import com.challenge.checkout.entity.product.ProductBase;
import com.challenge.checkout.entity.product.ProductWithPromotions;
import com.challenge.checkout.exception.BadRequestException;
import com.challenge.checkout.gateway.GatewayMarketStrategy;
import com.challenge.checkout.gateway.WiremockGateway;
import com.challenge.checkout.model.TenantModel;
import com.challenge.checkout.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    GatewayMarketStrategy gatewayMarketStrategy;

    @Autowired
    TenantRepository tenantRepository;

    public ProductWithPromotions getProductWithPromotions(String tenantName, String productId) {
        TenantModel tenantModel = tenantRepository.findByName(tenantName).orElseThrow(
                () -> new BadRequestException(String.format("Tenant %s not found.", tenantName))
        );

        WiremockGateway wiremockGateway = gatewayMarketStrategy.getMarketGateway(tenantModel.getName());
        return wiremockGateway.getProductWithPromotions(tenantModel.getBaseURL(), productId);
    }

    public List<ProductBase> getProducts(String tenantName) {
        TenantModel tenantModel = tenantRepository.findByName(tenantName).orElseThrow(
                () -> new BadRequestException(String.format("Tenant %s not found.", tenantName))
        );

        WiremockGateway wiremockGateway = gatewayMarketStrategy.getMarketGateway(tenantModel.getName());
        return wiremockGateway.getProducts(tenantModel.getBaseURL());
    }
}
