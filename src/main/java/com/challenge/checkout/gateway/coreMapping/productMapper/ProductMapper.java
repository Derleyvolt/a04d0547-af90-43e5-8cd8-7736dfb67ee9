package com.challenge.checkout.gateway.coreMapping.productMapper;

import com.challenge.checkout.exception.BadRequestException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.challenge.checkout.entity.product.ProductWithPromotions;
import com.challenge.checkout.entity.product.ProductBase;
import com.challenge.checkout.gateway.ProductGatewayMapper;
import com.challenge.checkout.gateway.PromotionGatewayMapper;
import com.challenge.checkout.gateway.coreMapping.PromotionFactoryRegistry;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public class ProductMapper implements ProductGatewayMapper {
    public ProductBase mapProductBase(Map<String, Object> json) {
        try {
            return new ProductBase(
                    json.get("id").toString(),
                    json.get("name").toString(),
                    new BigInteger(json.get("price").toString())
            );
        } catch (Exception e) {
            throw new BadRequestException("Error parsing product base from JSON");
        }
    }

    public ProductWithPromotions mapProductWithPromotions(Map<String, Object> json) {
        try {
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
                                        PromotionFactoryRegistry.getPromotionGatewayMapper(promotionType);
                                return promotionMapper.map(jsonPromotion);
                            }
                    ).toList()
            );

            return product;
        } catch (Exception e) {
            throw new BadRequestException("Error parsing product with promotions from JSON");
        }
    }
}
