package com.challenge.checkout.gateway.marketA;

import com.challenge.checkout.entity.product.ProductBase;
import com.challenge.checkout.entity.product.ProductWithPromotions;
import com.challenge.checkout.gateway.WiremockGateway;
import com.challenge.checkout.gateway.marketA.productMapper.ProductMapperMarketA;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Component
public class WiremockMarketAAdapter implements WiremockGateway {
    @Autowired
    private WiremockMarketA wiremockMarketA;

    @Override
    public String getTenantName() {
        return wiremockMarketA.getTenantName();
    }

    @Override
    public List<ProductBase> getProducts(String baseURL) {
        List<Map<String, Object>> jsonProducts = wiremockMarketA.getProducts(baseURL);
        ProductMapperMarketA productMapper = new ProductMapperMarketA();

        return jsonProducts.stream().map(productMapper::mapProductBase).toList();
    }

    @Override
    public ProductWithPromotions getProductWithPromotions(String baseURL, String productId) {
        Map<String, Object> jsonProductWithPromotions = wiremockMarketA.getProductWithPromotion(baseURL, productId);
        ProductMapperMarketA productWithPromotionsMapper = new ProductMapperMarketA();

        return productWithPromotionsMapper.mapProductWithPromotions(
                jsonProductWithPromotions
        );
    }
}