package com.challenge.checkout.gateway.customMapping.marketX;

import com.challenge.checkout.entity.product.ProductBase;
import com.challenge.checkout.entity.product.ProductWithPromotions;
import com.challenge.checkout.gateway.MappingFormatEnum;
import com.challenge.checkout.gateway.MarketGateway;
import com.challenge.checkout.gateway.WiremockGateway;
import com.challenge.checkout.gateway.customMapping.marketX.productMapper.ProductMapperMarketX;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Component
public class WiremockAdapterMarketX implements WiremockGateway {
    @Autowired
    private MarketGateway marketGateway;

    @Override
    public List<ProductBase> getProducts(String baseURL) {
        List<Map<String, Object>> jsonProducts = marketGateway.getProducts(baseURL);
        ProductMapperMarketX productMapper = new ProductMapperMarketX();

        return jsonProducts.stream().map(productMapper::mapProductBase).toList();
    }

    @Override
    public ProductWithPromotions getProductWithPromotions(String baseURL, String productId) {
        Map<String, Object> jsonProductWithPromotions = marketGateway.getProductWithPromotion(baseURL, productId);
        ProductMapperMarketX productWithPromotionsMapper = new ProductMapperMarketX();

        return productWithPromotionsMapper.mapProductWithPromotions(
                jsonProductWithPromotions
        );
    }

    @Override
    public MappingFormatEnum getMappingFormat() {
        return MappingFormatEnum.MARKET_X_FORMAT;
    }
}