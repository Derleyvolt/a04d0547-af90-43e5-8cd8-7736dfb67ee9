package com.test.checkout.gateway.marketA.productMapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.checkout.entity.product.ProductWithPromotions;
import com.test.checkout.entity.product.ProductBase;
import com.test.checkout.gateway.ProductGatewayMapper;
import com.test.checkout.gateway.PromotionGatewayMapper;
import com.test.checkout.gateway.marketA.PromotionFactoryRegistryMarketA;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public class ProductMapperMarketA implements ProductGatewayMapper {
    public ProductBase mapProductBase(Map<String, Object> json) {
        return new ProductBase(
                json.get("id").toString(),
                json.get("name").toString(),
                new BigInteger(json.get("price").toString())
        );
    }

    public ProductWithPromotions mapProductWithPromotions(Map<String, Object> json) {
        ProductWithPromotions product = new ProductWithPromotions();

        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, Object>> jsonPromotions = objectMapper.convertValue(
                json.get("promotions"),
                new TypeReference<>() {}
        );

        product.setId(json.get("id").toString());
        product.setName(json.get("name").toString());
        product.setPrice(new BigInteger(json.get("price").toString()));

        product.setPromotions(
                jsonPromotions.stream().map(
                        jsonPromotion -> {
                            String promotionType = jsonPromotion.get("type").toString();
                            PromotionGatewayMapper promotionMapper =
                                    PromotionFactoryRegistryMarketA.getPromotionGatewayMapper(promotionType);
                            return promotionMapper.map(jsonPromotion);
                        }
                ).toList()
        );

        return product;
    }
}
