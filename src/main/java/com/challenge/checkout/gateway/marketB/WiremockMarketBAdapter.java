package com.challenge.checkout.gateway.marketB;

import com.challenge.checkout.entity.product.ProductBase;
import com.challenge.checkout.entity.product.ProductWithPromotions;
import com.challenge.checkout.gateway.WiremockGateway;
import com.challenge.checkout.gateway.marketB.productMapper.ProductMapperMarketB;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Component
public class WiremockMarketBAdapter implements WiremockGateway {
    @Autowired
    private WiremockMarketB wiremockMarketB;

    @Override
    public String getTenantName() {
        return wiremockMarketB.getTenantName();
    }

    @Override
    public List<ProductBase> getProducts(String baseURL) {
        List<Map<String, Object>> jsonProducts = wiremockMarketB.getProducts(baseURL);
        ProductMapperMarketB productMapper = new ProductMapperMarketB();

        return jsonProducts.stream().map(productMapper::mapProductBase).toList();
    }

    @Override
    public ProductWithPromotions getProductWithPromotions(String baseURL, String productId) {
        Map<String, Object> jsonProductWithPromotions = wiremockMarketB.getProductWithPromotion(baseURL, productId);
        ProductMapperMarketB productWithPromotionsMapper = new ProductMapperMarketB();

        return productWithPromotionsMapper.mapProductWithPromotions(
                jsonProductWithPromotions
        );
    }
}